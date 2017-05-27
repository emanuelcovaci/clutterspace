package com.tmc.clutterspace.core.engine;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.tmc.clutterspace.core.engine.components.Component;
import com.tmc.clutterspace.core.exceptions.ComponentNotFoundException;
import com.tmc.clutterspace.core.exceptions.LinkedComponentException;

/**
 * {@link GameObject} is a container used to store {@link Component}(s).
 * The {@link GameObject} calls the {@link Component#update(float)}, 
 * {@link Component#render()} etc.
 * <p>
 * See {@link #GameObject()} for more information.
 * @author roadd
 *
 */
public class GameObject {
	private ClassToInstanceMap<Component> components = MutableClassToInstanceMap.create();
	private static int lastId = 0;
	protected long disposeTime = -1;
	/**
	 * Identification id of this {@link GameObject}.
	 */
	public int id;

	/**
	 * Give an unique id to the last created {@link GameObject}.
	 * @return The last id.
	 */
	private static int takeInEvidence(){
		return lastId++;
	}
	
	public boolean isDisposed(){
		return disposeTime != -1 && Engine.getInstance().getTime() > disposeTime; 
	}
	
	public void setDisposed(long time){
		disposeTime = time;
		components.values().stream().forEach(a -> a.setDisposed(time));
	}
	
	public GameObject(){
		id = GameObject.takeInEvidence();
	}
	
	protected GameObject(int id){
		this.id = id;
	}
	
	/**
	   * Adds/Sets a new {@link Component} for this {@link GameObject}.
	   * @param <T> The type of the {@link Component} that will be added.
	   * @param comp The {@link Component} that will be added.
	   * @throws LinkedComponentException (optional)
	   */
	public <T extends Component> void setComponent(T comp){
		System.out.println(comp.getGameObject());
		if(comp.getGameObject() != null)
			throw new LinkedComponentException();
		if(hasComponent(comp.getClass())){
			removeComponent(comp.getClass());
		}
		components.put(comp.getClass(), comp);
		comp.setGameObject(this);
	}

	/**
	   * This function returns the {@link Component} of the specified type.
	   * @param <T> The type of the returned {@link Component}.
	   * @param compType The class of the returned {@link Component}.
	   * @return The {@link Component} of the specified type.
	   * @throws ComponentNotFoundException (optional)
	   */
	public <T extends Component> T getComponent(Class<T> compType){
		if(!hasComponent(compType)){
			throw new ComponentNotFoundException(compType);
		}
		return components.getInstance(compType);
	}
	
	/**
	   * Checks if {@link GameObject} has the specified {@link Component} type.
	   * @param <T> The type of the {@link Component} we check for.
	   * @param compType The class of the {@link Component} we check for.
	   * @return {@link true} if the object contains the specified {@link Component} type.
	   */
	public <T extends Component> boolean hasComponent(Class<T> compType){
		if(!components.containsKey(compType)){
			return false;
		}
		else if(components.containsKey(compType) && components.getInstance(compType) == null){
			return false;
		}
		return true;
	}
	
	/**
	   * Removes the {@link Component} of the specified type. Return {@link null} 
	   * if the {@link Component} we want to remove is non-existent.
	   * @param <T> The type of the {@link Component} we check for.
	   * @param compType The class of the {@link Component} we check for.
	   * @return The removed {@link Component} or {@link null}.
	   */
	@SuppressWarnings("unchecked")
	public <T extends Component> T removeComponent(Class<T> compType){
		if(hasComponent(compType)){
			T comp = ((T) components.remove(compType));
			comp.setGameObject(null);
			return comp;
		}
		return null;
	}
	
	/**
	 * Serializes the render {@link State State(s)} of the {@link GameObject}.
	 * @return The serialized form(byte array).
	 * @throws IOException
	 */
	public byte[] serialize() throws IOException{
		ArrayList<byte[]> barr = new ArrayList<byte[]>();
		ByteBuffer buf = ByteBuffer.allocate(8);
		buf.putInt(id);
		
		int nr = 0;

		ArrayList<byte[]> barr2 = new ArrayList<byte[]>();
		for(Component c : components.values()){
			if(c.getState() == null) continue;
			byte[] a = c.getState().serialize();
			if(a.length != 0){
				nr++;
				barr2.add(a);
			}
		}
		if(nr == 0) return new byte[]{};

		buf.putInt(nr);
		barr.add(buf.array());
		barr.addAll(barr2);
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		for(byte[] val : barr)
			out.write(val);
		
		return baos.toByteArray();
	}
	
	/**
	 * Deserializes the render {@link State State(s)} of the {@link GameObject}. 
	 * @param barr The serialized {@link GameObject}.
	 * @return An {@link ArrayList} containing all the render {@link State State(s)}. 
	 */
	public static GameObject deserialize(byte[] barr){
		ArrayList<State> states = new ArrayList<State>();
		ByteBuffer buf = ByteBuffer.wrap(barr);
		int id = buf.getInt();
		int nr = buf.getInt();
		while(nr > 0){
			buf.getInt();
			int nrVal = buf.getInt();
			byte[] arr = new byte[nrVal + 8];
			buf.position(buf.position() - 8);
//			System.out.println(buf.limit() + " " + nrVal);
			buf.get(arr, 0, nrVal + 8);
			states.add(State.deserialize(arr));
			nr--;
		}
		
		GameObject obj = new GameObject(id);
		for(State s : states){
			Method m;
			try {
				m = Component.Dictionary.inverse().get(s.typeId).getMethod("fromState", State.class);
				Component comp = (Component)m.invoke(null, s);
				if(comp != null)
					obj.setComponent(comp);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		return obj;
	}
	
	public GameObject interpolate(GameObject other, float perc){
		GameObject ret = new GameObject();
		
		if(other.isDisposed()) return null;
		
		for(Component c : components.values()){}
//			ret.setComponent(c.int);
		
		return ret;
	}
	
	public String toString(){
		return "ID:" + id + " Comps: " + components.keySet().stream().map(Class::getName).collect(Collectors.joining(","));
	}
	
	public void init(){
		if(isDisposed()) return;
		components.values().stream().forEach(a -> a.init());
	}
	
	public void prepare(){
		if(isDisposed()) return;
		components.values().stream().forEach(a -> a.prepare());
	}
	
	public void update(float delta){
		if(isDisposed()) return;
		components.values().stream().forEach(a -> a.update(delta));
	}
	
	public void postUpdate(){
		if(isDisposed()) return;
		components.values().stream().forEach(a -> a.postUpdate());
	}
	
	public void render(SpriteBatch batch){
		if(isDisposed()) return;
		components.values().stream().forEach(a -> a.render(batch));
	}
	
	public void onGui(SpriteBatch batch){
		if(isDisposed()) return;
		components.values().stream().forEach(a -> a.onGui(batch));
	}
}

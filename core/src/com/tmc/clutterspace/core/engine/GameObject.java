package com.tmc.clutterspace.core.engine;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

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
	
	/**
	 * Identification id of this {@link GameObject}.
	 */
	public int id = GameObject.takeInEvidence();

	/**
	 * Give an unique id to the last created {@link GameObject}.
	 * @return The last id.
	 */
	private static int takeInEvidence(){
		return lastId++;
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
	public byte[] serializeRenderStates() throws IOException{
		ArrayList<byte[]> barr = new ArrayList<byte[]>();
		ByteBuffer buf = ByteBuffer.allocate(4);
		barr.add(buf.putInt(id).array());
		
		int nr = 0;

		ArrayList<byte[]> barr2 = new ArrayList<byte[]>();
		for(Component c : components.values()){
			byte[] a = c.getState().serialize();
			if(a.length != 0){
				nr++;
				barr2.add(a);
			}
		}
		if(nr == 0) return new byte[]{};

		buf.clear();
		barr.add(buf.putInt(nr).array());
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
	public static ArrayList<State> deserializeRenderStates(byte[] barr){
		ArrayList<State> states = new ArrayList<State>();
		ByteBuffer buf = ByteBuffer.wrap(barr);
		buf.getInt();
		int nr = buf.getInt();
		while(nr > 0){
			buf.getInt();
			int nrVal = buf.getInt();
			byte[] arr = new byte[nrVal + 8];
			buf.position(buf.position() - 8);
			buf.get(arr, 0, nrVal + 8);
			states.add(State.deserialize(arr));
			nr--;
		}
		
		return states;
	}
	
	public void init(){
		components.values().stream().forEach(a -> a.init());
	}
	
	public void prepare(){
		components.values().stream().forEach(a -> a.prepare());
	}
	
	public void update(float delta){
		components.values().stream().forEach(a -> a.update(delta));
	}
	
	public void postUpdate(){
		components.values().stream().forEach(a -> a.postUpdate());
	}
	
	public void render(SpriteBatch batch){
		components.values().stream().forEach(a -> a.render(batch));
	}
	
	public void onGui(SpriteBatch batch){
		components.values().stream().forEach(a -> a.onGui(batch));
	}
}

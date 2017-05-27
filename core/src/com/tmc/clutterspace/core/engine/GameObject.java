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
	
	public byte[] serialize() throws IOException{
		ArrayList<Integer> barr = new ArrayList<Integer>();
		barr.add(id);
		barr.add(components.size());

		
		for(Component c : components.values()){
			ArrayList<Integer> a = c.getState().getSerializableData();
			if(a.isEmpty()) barr.set(1, barr.get(1) - 1);
			else barr.addAll(a);
		}
		
		if(barr.get(1) == 0) return new byte[]{};
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		for(Integer val : barr)
			out.writeInt(val);
		
		return baos.toByteArray();
	}
	
	public static ArrayList<State> deserialize(byte[] barr){
		ArrayList<State> states = new ArrayList<State>();
		IntBuffer intBuf = ByteBuffer.wrap(barr)
				     				 .order(ByteOrder.BIG_ENDIAN)
				     				 .asIntBuffer();
		int[] a = new int[intBuf.limit()];
		intBuf.get(a);
		
		int nr = a[1];
		
		int i = 2;
		while(nr > 0){
			int nrVal = a[i+1];
			states.add(State.deserialize(Arrays.copyOfRange(barr, i * 4, (i + 2 + nrVal) * 4)));
			i += 2 + nrVal;
			nr--;
		}
		
		return states;
	}
	
	
}

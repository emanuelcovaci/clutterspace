package com.tmc.clutterspace.core.engine;

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
	private ClassToInstanceMap<Component> components;

	/**
	   * Contructor for a new {@link GameObject}.
	   */
	public GameObject(){
		components = MutableClassToInstanceMap.create();
	}

	/**
	   * Adds/Sets a new {@link Component} for this {@link GameObject}.
	   * @param <T> The type of the {@link Component} that will be added.
	   * @param comp The {@link Component} that will be added.
	   * @throws LinkedComponentException (optional)
	   */
	public <T extends Component> void setComponent(T comp){
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
}

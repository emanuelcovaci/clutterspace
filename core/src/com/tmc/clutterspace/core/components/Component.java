package com.tmc.clutterspace.core.components;

import java.util.ArrayList;
import com.tmc.clutterspace.core.components.exceptions.MissingDependencyException;
import com.tmc.clutterspace.core.components.exceptions.ComponentNotFoundException;
import com.tmc.clutterspace.core.components.exceptions.LinkedComponentException;
import com.tmc.clutterspace.core.objects.GameObject;

/**
 * Abstract base class used for components. It implements basic 
 * functionality of a {@link Component}.
 * @author roadd
 *
 */
public abstract class Component {
	protected GameObject gameObject;
	protected ArrayList<Class<? extends Component>> dependencies;
	private boolean init = false;
	
	/**
	   * Returns the {@link GameObject} this {@link Component} is attached to.
	   * @return The attached {@link GameObject} .
	   */
	public GameObject getGameObject() {
		return gameObject;
	}
	
	/**
	   * Sets the {@link GameObject} this {@link Component} is attached to.
	   * @throws LinkedComponentException (optional)
	   */
	public void setGameObject(GameObject gameObject) {
		if(this.gameObject == null)
			this.gameObject = gameObject;
		throw new LinkedComponentException();
	}

	/**
	   * Forces the existence of all dependencies. If one is 
	   * not found, a runtime exception is thrown.
	   * @throws MissingDependencyException (optional)
	   */
	private void forceCheckDependencies(){
		for(Class<? extends Component> e : dependencies){
			if(!gameObject.hasComponent(e))
				throw new MissingDependencyException(e);
		}
	}
	
	/**
	   * Checks if all dependencies are satisfied.
	   * @return {@link true} if all components are satisfied, otherwise {@link false}.
	   */
	public boolean checkDependencies(){
		for(Class<? extends Component> e : dependencies){
			if(!gameObject.hasComponent(e))
				return false;	
		}
		return true;
	}
	
	/**
	   * Wrapper function for {@link #initImpl()}.
	   * <p>
	   * Call this function to initialize all dependencies / post 
	   * constructor initializationof the {@link Component}.
	   */
	public void init(){
		if(!init) {
			init = true;
			forceCheckDependencies();
			initImpl();
		}
	}
	
	/**
	   * This function should be overridden to add the implementation 
	   * of the {@link #init()}.
	   */
	protected abstract void initImpl();
	
	/**
	   * Wrapper function for {@link #updateImpl(delta)}.
	   * <p>
	   * Call this function to update the {@link Component}.
	   * @param delta The ammount of time that is simulated.
	   */
	public void update(float delta){
		if(!init) throw new RuntimeException();
		forceCheckDependencies();
		updateImpl(delta);
	}
	
	/**
	   * This function should be overridden to add the implementation 
	   * of the {@link #update(delta)}.
	   * @param delta The ammount of time that is simulated.
	   */
	protected abstract void updateImpl(float delta);
	
	/**
	   * Wrapper function for {@link #renderImpl()}.
	   * <p>
	   * Call this function to render the {@link Component}.
	   */
	public void render(){
		if(!init) throw new RuntimeException();
		forceCheckDependencies();
		renderImpl();
	}
	
	/**
	   * This function should be overridden to add the implementation 
	   * of the {@link #render(delta)}.
	   */
	protected abstract void renderImpl();
}

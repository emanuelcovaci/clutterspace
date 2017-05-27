package com.tmc.clutterspace.core.engine.components;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.State;
import com.tmc.clutterspace.core.exceptions.ComponentNotFoundException;
import com.tmc.clutterspace.core.exceptions.LinkedComponentException;
import com.tmc.clutterspace.core.exceptions.MissingDependencyException;

/**
 * Abstract base class used for components. It implements basic 
 * functionality of a {@link Component}.
 * @author roadd
 *
 */
@SuppressWarnings("unchecked")
public abstract class Component {
	public static BiMap<Class<? extends Component>, Integer> Dictionary = HashBiMap.create();
	protected static int lastId = 0;
	
	static {
		register(Component.class);
	}
	
	protected GameObject gameObject = null;
	private ArrayList<Class<? extends Component>> dependencies = new ArrayList<Class<? extends Component>>();
	private boolean init = false;
	
	protected static <T extends Component> void register(Class<T> clazz){
		Dictionary.put(clazz, lastId);
		lastId++;
	}
	
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
		if(this.gameObject == null){
			this.gameObject = gameObject;
			return;
		}
		throw new LinkedComponentException();
	}

	/**
	   * Forces the existence of all dependencies. If one is 
	   * not found, a runtime exception is thrown.
	   * @throws MissingDependencyException (optional)
	   */
	private void forceCheckDependencies(){
		for(Class<? extends Component> e : getDependencies()){
			if(!gameObject.hasComponent(e))
				throw new MissingDependencyException(e);
		}
	}
	
	/**
	   * Checks if all dependencies are satisfied.
	   * @return {@link true} if all components are satisfied, otherwise {@link false}.
	   */
	public boolean checkDependencies(){
		for(Class<? extends Component> e : getDependencies()){
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
	   * Wrapper function for {@link #prepareImpl()}.
	   * <p>
	   * Call this function to prepare the {@link Component}.
	   */
	public void prepare(){
		if(!init) throw new RuntimeException();
		forceCheckDependencies();
		prepareImpl();
	}
	
	/**
	   * This function should be overridden to add the implementation 
	   * of the {@link #prepare()}.
	   */
	protected abstract void prepareImpl();
	
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
	   * Wrapper function for {@link #postUpdateImpl(delta)}.
	   * <p>
	   * Call this function to update the {@link Component} after physics.
	   */
	public void postUpdate(){
		if(!init) throw new RuntimeException();
		forceCheckDependencies();
		postUpdateImpl();
	}
	
	/**
	   * This function should be overridden to add the implementation 
	   * of the {@link #postUpdate(delta)}.
	   */
	protected abstract void postUpdateImpl();
	
	/**
	   * Wrapper function for {@link #renderImpl()}.
	   * <p>
	   * Call this function to render the {@link Component}.
	   * @param batch The render batch.
	   */
	public void render(SpriteBatch batch){
		if(!init) throw new RuntimeException();
		forceCheckDependencies();
		renderImpl(batch);
	}
	
	/**
	   * This function should be overridden to add the implementation 
	   * of the {@link #render()}.
	   * @param batch The render batch.
	   */
	protected abstract void renderImpl(SpriteBatch batch);
	
	/**
	   * Wrapper function for {@link #onGuiImpl()}.
	   * <p>
	   * Call this function to draw on gui for the {@link Component}.
	   * @param batch The render batch.
	   */
	public void onGui(SpriteBatch batch){
		if(!init) throw new RuntimeException();
		forceCheckDependencies();
		onGuiImpl(batch);
	}
	
	/**
	   * This function should be overridden to add the implementation 
	   * of the {@link #onGui()}.
	   * @param batch The render batch.
	   */
	protected abstract void onGuiImpl(SpriteBatch batch);

	/**
	 * Return all dependecies of this {@link Component}.
	 * @return The dependencies.
	 */
	public ArrayList<Class<? extends Component>> getDependencies() {
		return dependencies;
	}
	
	/**
	 * Returns the {@link Component Component(s)} current {@link State}.
	 * @return The current {@link State}.
	 */
	public abstract State getState();
}

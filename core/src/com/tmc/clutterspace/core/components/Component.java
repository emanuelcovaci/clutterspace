package com.tmc.clutterspace.core.components;

import java.util.ArrayList;
import com.tmc.clutterspace.core.components.exceptions.DependencyException;
import com.tmc.clutterspace.core.components.exceptions.MissingComponentException;
import com.tmc.clutterspace.core.objects.GameObject;

public abstract class Component {
	protected GameObject gameObject;
	protected ArrayList<Class<? extends Component>> dependencies;
	
	
	public GameObject getGameObject() {
		return gameObject;
	}

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	public boolean checkDependencies(){
		for(Class<? extends Component> e : dependencies){
			try{
				gameObject.getComponent(e);
			}
			catch(MissingComponentException ex){
				throw new DependencyException(e);
			}
		}
		return true;
	}
	
	public void update(float delta){
		checkDependencies();
		updateImpl(delta);
	}
	
	protected abstract void updateImpl(float delta);
	

	public void render(){
		checkDependencies();
		renderImpl();
	}
	
	protected abstract void renderImpl();
}

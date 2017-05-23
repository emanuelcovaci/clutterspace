package com.tmc.clutterspace.core.objects;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.tmc.clutterspace.core.components.Component;
import com.tmc.clutterspace.core.components.exceptions.MissingComponentException;

public class GameObject {
	private ClassToInstanceMap<Component> components;
	
	public GameObject(){
		components = MutableClassToInstanceMap.create();
	}
	
	public <T extends Component> void setComponent(T comp){
		if(components.containsKey(comp.getClass())){
			comp.setGameObject(null);
		}
		components.put(comp.getClass(), comp);
		comp.setGameObject(this);
	}
	
	public <T extends Component> T getComponent(Class<T> compType){
		if(!components.containsKey(compType)){
			throw new MissingComponentException(compType);
		}
		return components.getInstance(compType);
	}
}

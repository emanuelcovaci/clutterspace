package com.tmc.clutterspace.core.components.exceptions;

import com.tmc.clutterspace.core.components.Component;

public class MissingComponentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8576508358169822327L;

	private Class<? extends Component> comp;
	
	public MissingComponentException(Class<? extends Component> comp) {
	    this.comp = comp;
	}
	
	public String getMessage(){
		return "The following component is missing from the game object: " + comp.getName();
	}
}

package com.tmc.clutterspace.core.exceptions;

import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.components.Component;

/**
 * Should be thrown when trying to add a {@link Component} which is already added to another {@link GameObject}.
 * @author roadd
 *
 */
public class LinkedComponentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6583311387151500897L;
	
	/**
	   * Returns the exception message.
	   * @return The exception message.
	   */
	public String getMessage(){
		return "The component that should be added to the GameObject is already linked to another GameObject.";
	}
}

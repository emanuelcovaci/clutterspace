package com.tmc.clutterspace.core.components.exceptions;

import com.tmc.clutterspace.core.components.Component;

/**
 * Should be thrown only when a {@link Component} is missing a {@link Component} that is dependent on.
 * <p>
 * See {@link #MissingDependencyException(Class)} for more information.
 * @author roadd
 *
 */
public class MissingDependencyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1796643582270576869L;
	
	public Class<? extends Component> compType;
	
	/**
	   * Contructor for the {@link MissingDependencyException}
	   * @param <T> The type of the {@link Component} that is missing.
	   * @param compType The class of the {@link Component} that is missing.
	   */
	public MissingDependencyException(Class<? extends Component> compType) {
	    this.compType = compType;
	}
	
	/**
	   * Returns the exception message.
	   * @return The exception message.
	   */
	public String getMessage(){
		return "The component is missing the following dependency " + compType.getName();
	}
}

package com.tmc.clutterspace.core.components.exceptions;

import com.tmc.clutterspace.core.components.Component;
import com.tmc.clutterspace.core.objects.GameObject;

/**
 * Should be only thrown when trying to get an unexisting {@link Component} from a {@link GameObject}.
 * <p>
 * See {@link #ComponentNotFoundException(Class)} for more information.
 * @author roadd
 *
 */
public class ComponentNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8576508358169822327L;

	public Class<? extends Component> compType;
	
	/**
	   * Contructor for the {@link ComponentNotFoundException}.
	   * @param <T> The type of the {@link Component} that is not found in 
	   * the {@link GameObject}.
	   * @param compType The class of the {@link Component} that is not found 
	   * in the {@link GameObject}.
	   */
	public ComponentNotFoundException(Class<? extends Component> compType) {
	    this.compType = compType;
	}
	
	/**
	   * Returns the exception message.
	   * @return The exception message.
	   */
	public String getMessage(){
		return "The following component is missing from the game object: " + compType.getName();
	}
}

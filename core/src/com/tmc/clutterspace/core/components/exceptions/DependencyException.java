package com.tmc.clutterspace.core.components.exceptions;

import com.tmc.clutterspace.core.components.Component;

public class DependencyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1796643582270576869L;
	
	private Class<? extends Component> dep;
	
	public DependencyException(Class<? extends Component> dep) {
	    this.dep = dep;
	}
	
	public String getMessage(){
		return "The component is missing the following dependency " + dep.getName();
	}
}

package com.tmc.clutterspace.core.components;

public class Body extends Component {

	public Body(){
		super();
		this.dependencies.add(Transform2D.class);
	}
	
	@Override
	protected void updateImpl(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void renderImpl() {
		// TODO Auto-generated method stub
		
	}

}

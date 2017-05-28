package com.tmc.clutterspace.core.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tmc.clutterspace.core.engine.*;
import com.tmc.clutterspace.core.engine.components.GroundSensor;
import com.tmc.clutterspace.core.engine.components.TileMap;

public class GroundContact implements ContactListener {
	@Override
	public void beginContact(Contact arg0) {
		Fixture fa = arg0.getFixtureA();
	    Fixture fb = arg0.getFixtureB();
	    if(fa.getUserData().getClass() == GameObject.class)
		    if(((GameObject)fa.getUserData()).hasComponent(TileMap.class) && fb.getUserData().getClass() == GroundSensor.class) {
		    	((GroundSensor)fb.getUserData()).onGround = true;
		    }
		    else if(((GameObject)fb.getUserData()).hasComponent(TileMap.class) && fa.getUserData().getClass() == GroundSensor.class) {
		    	((GroundSensor)fa.getUserData()).onGround = true;
		    }
	}

	@Override
	public void endContact(Contact arg0) {
		Fixture fa = arg0.getFixtureA();
	    Fixture fb = arg0.getFixtureB();
	    
		if(fa.getUserData().getClass() == GameObject.class)
		    if(((GameObject)fa.getUserData()).hasComponent(TileMap.class) && fb.getUserData().getClass() == GroundSensor.class) {
		    	((GroundSensor)fb.getUserData()).onGround = false;
		    }
		    else if(((GameObject)fb.getUserData()).hasComponent(TileMap.class) && fa.getUserData().getClass() == GroundSensor.class) {
		    	((GroundSensor)fa.getUserData()).onGround = false;
		    }

	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
	}

}

package com.tmc.clutterspace.core.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tmc.clutterspace.core.engine.Engine;
import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.components.GroundSensor;
import com.tmc.clutterspace.core.engine.components.Health;
import com.tmc.clutterspace.core.engine.components.Projectile;
import com.tmc.clutterspace.core.engine.components.TileMap;

public class BulletPlayerContact implements ContactListener {

	@Override
	public void beginContact(Contact arg0) {
		Fixture fa = arg0.getFixtureA();
	    Fixture fb = arg0.getFixtureB();
	    if(fb.getUserData().getClass() == GameObject.class && fa.getUserData().getClass() == GameObject.class)
	    	if(((GameObject)fb.getUserData()).id != ((GameObject)fa.getUserData()).id)
			    if(((GameObject)fa.getUserData()).hasComponent(Projectile.class)) {
			    	if(((GameObject)fb.getUserData()).hasComponent(Health.class)){
			    		Health comp = ((GameObject)fb.getUserData()).getComponent(Health.class);
			    		comp.setHealth(comp.getHealth() - ((GameObject)fa.getUserData()).getComponent(Projectile.class).getDmg());
			    	}
			    	((GameObject)fa.getUserData()).setDisposed(Engine.getInstance().getTime());
			    }
			    else if(((GameObject)fb.getUserData()).hasComponent(Projectile.class)) {
			    	System.out.println(((GameObject)fb.getUserData()).getComponent(Projectile.class).getDmg());
			    	if(((GameObject)fa.getUserData()).hasComponent(Health.class)){
			    		Health comp = ((GameObject)fa.getUserData()).getComponent(Health.class);
			    		comp.setHealth(comp.getHealth() - ((GameObject)fb.getUserData()).getComponent(Projectile.class).getDmg());
			    	}
			    	((GameObject)fb.getUserData()).setDisposed(Engine.getInstance().getTime());
			    }
	}

	@Override
	public void endContact(Contact arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub

	}

}

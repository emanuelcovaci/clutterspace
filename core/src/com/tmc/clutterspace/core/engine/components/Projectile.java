package com.tmc.clutterspace.core.engine.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tmc.clutterspace.core.collision.BulletPlayerContact;
import com.tmc.clutterspace.core.engine.Engine;
import com.tmc.clutterspace.core.engine.State;

public class Projectile extends Component {
	static{
		register(Projectile.class);
	}
	
	private float dmg;
	private Vector2 dir;
	
	public Projectile(float dmg, Vector2 dir){
		getDependencies().add(Sprite2D.class);
		getDependencies().add(Body2D.class);
		this.dmg = dmg;
		this.dir = dir;
	}

	@Override
	protected void initImpl() {
		Body2D body = getGameObject().getComponent(Body2D.class);
		body.init();
		body.getBody().setLinearVelocity(dir);
	}

	@Override
	protected void prepareImpl() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateImpl(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void postUpdateImpl() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void preRenderImpl(SpriteBatch batch) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void renderImpl(SpriteBatch batch) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onGuiImpl(SpriteBatch batch) {
		// TODO Auto-generated method stub

	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component interpolateImpl(Component other, float perc) {
		// TODO Auto-generated method stub
		return null;
	}

	public float getDmg() {
		return dmg;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}

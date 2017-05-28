package com.tmc.clutterspace.core.engine.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tmc.clutterspace.core.collision.CollisionDict;
import com.tmc.clutterspace.core.collision.GroundContact;
import com.tmc.clutterspace.core.engine.Engine;
import com.tmc.clutterspace.core.engine.State;

public class GroundSensor extends Component {
	static{
		register(GroundSensor.class);
	    Engine.getInstance().getWorld().setContactListener(new GroundContact());
	}
	
	public GroundSensor(){
		getDependencies().add(Body2D.class);
		getDependencies().add(Sprite2D.class);
	}
	
	public boolean onGround;
	@Override
	protected void initImpl() {
		Sprite2D spr = getGameObject().getComponent(Sprite2D.class);
		spr.init();
		Body2D body = getGameObject().getComponent(Body2D.class);
		body.init();
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(spr.size.x / 3, 10, new Vector2(0, -spr.size.y), 0);
		fixtureDef.shape = boxShape;
	    fixtureDef.filter.categoryBits = CollisionDict.CATEGORY_PLAYER;
	    fixtureDef.filter.maskBits = CollisionDict.CATEGORY_GROUND;
	    fixtureDef.isSensor=true;
	    body.getBody().createFixture(fixtureDef).setUserData(this);
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

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}

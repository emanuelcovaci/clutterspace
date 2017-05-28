package com.tmc.clutterspace.core.engine.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tmc.clutterspace.core.collision.CollisionDict;
import com.tmc.clutterspace.core.engine.Engine;
import com.tmc.clutterspace.core.engine.State;

/**
 * Created by emanuel on 27.05.2017.
 */
public class Player extends  Component{
    static {
        register(Player.class);
    }

    public Player(){
        getDependencies().add(Body2D.class);
        getDependencies().add(Transform2D.class);
        getDependencies().add(GroundSensor.class);
        getDependencies().add(Sprite2D.class);
    }


    @Override
    protected void initImpl() {
    	Sprite2D spr = getGameObject().getComponent(Sprite2D.class);
    	spr.init();
        Body2D comp = getGameObject().getComponent(Body2D.class);
        comp.init();
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(spr.size.x, spr.size.y - spr.size.x / 2, new Vector2(0, 0), 0);
		fixtureDef.shape = boxShape;
	    fixtureDef.filter.categoryBits = CollisionDict.CATEGORY_PLAYER;
	    fixtureDef.filter.maskBits = CollisionDict.MASK_PLAYER;
        fixtureDef.density = 2f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.0f;
        comp.getBody().createFixture(fixtureDef).setUserData(getGameObject());
        CircleShape cerc = new CircleShape();
        cerc.setRadius(spr.size.x);
        cerc.setPosition(new Vector2(0, -spr.size.x / 2));
        fixtureDef.shape = cerc;
        comp.getBody().createFixture(fixtureDef).setUserData(getGameObject());
    }

    @Override
    protected void prepareImpl() {

    }

    @Override
    protected void updateImpl(float delta) {
        Body2D comp = getGameObject().getComponent(Body2D.class);
        Transform2D trans = getGameObject().getComponent(Transform2D.class);
        GroundSensor ground = getGameObject().getComponent(GroundSensor.class);
        
        if(Engine.Inputs.A || Engine.Inputs.LEFT){
            comp.getBody().applyLinearImpulse(-0.80f * 10000,0, trans.p.x, trans.p.y,true);

        }
        if(Engine.Inputs.D || Engine.Inputs.RIGHT) {
            comp.getBody().applyLinearImpulse(0.80f* 10000, 0, trans.p.x, trans.p.y, true);
        }
        if(Engine.Inputs.SPACE && ground.onGround){
            comp.getBody().applyLinearImpulse(0,0.80f* 10000000, trans.p.x, trans.p.y,true);
        }
        if(Engine.Inputs.R){
            comp.getBody().setTransform(50,50,50);

        }
    }

    @Override
    protected void postUpdateImpl() {

    }

    @Override
    protected void renderImpl(SpriteBatch batch) {

    }

    @Override
    protected void onGuiImpl(SpriteBatch batch) {

    }

    @Override
    public State getState() {
        return null;
    }


	@Override
	public Component interpolateImpl(Component other, float perc) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected void preRenderImpl(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}

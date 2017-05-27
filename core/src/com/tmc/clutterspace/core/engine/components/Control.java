package com.tmc.clutterspace.core.engine.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tmc.clutterspace.core.engine.State;

/**
 * Created by emanuel on 27.05.2017.
 */
public class Control extends  Component{
    static {
        register(Control.class);
    }

    public Control(){
        getDependencies().add(Body2D.class);
    }


    @Override
    protected void initImpl() {

    }

    @Override
    protected void prepareImpl() {

    }

    @Override
    protected void updateImpl(float delta) {
        Body2D comp = getGameObject().getComponent(Body2D.class);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            comp.getBody().applyForceToCenter(-1000000,0,true);

        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            comp.getBody().applyForceToCenter(1000000, 0, true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            comp.getBody().applyForceToCenter(0,1000000,true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            comp.getBody().applyForceToCenter(0,-10000000,true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.R)){
            comp.getBody().setTransform(50,120,50);

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
}

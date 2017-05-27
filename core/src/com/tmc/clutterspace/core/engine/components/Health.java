package com.tmc.clutterspace.core.engine.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tmc.clutterspace.core.engine.State;

/**
 * Created by emanuel on 27.05.2017.
 */
public class Health extends Component {

    static {
        register(Health.class);
    }
    private Integer health;

    public Health(){
        health = 100;
    }

    public int getHealth(){
        return health;
    }

    public void setHealth(Integer x){
        health =  x;
    }

    @Override
    protected void initImpl() {

    }

    @Override
    protected void prepareImpl() {

    }

    @Override
    protected void updateImpl(float delta) {

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

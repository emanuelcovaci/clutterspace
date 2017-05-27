package com.tmc.clutterspace.core.engine.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.State;

/**
 * Created by emanuel on 27.05.2017.
 */
public class Health extends Component {

    static {
        register(Health.class);
    }
    private Integer health;
    BitmapFont font ;
    GameObject iconHearth;

    public Health(){
        health = 100;
        this.getDependencies().add(Transform2D.class);

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

        font = new BitmapFont();

        String x = Integer.toString(getHealth());

        font.draw(batch,x, 700, 580);

    }

    @Override
    protected void onGuiImpl(SpriteBatch batch) {

    }

    @Override
    public State getState() {
        return null;
    }
}

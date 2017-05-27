package com.tmc.clutterspace.core.engine.components;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tmc.clutterspace.core.engine.State;

/**
 * Created by emanuel on 27.05.2017.
 */
public class Light extends Component {
    static {
        register(Light.class);
    }
    private  PointLight pointLight;

    public Light(RayHandler rayHandler,Integer rays,Float distance,Integer x,Integer y){

        pointLight = new PointLight(rayHandler,rays, new Color(165,44,52,1),distance,x,y);


    }
    public PointLight getPointLight(){
        return pointLight;
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

package com.tmc.clutterspace.core.engine.components;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tmc.clutterspace.core.engine.Engine;
import com.tmc.clutterspace.core.engine.State;

/**
 * Created by emanuel on 27.05.2017.
 */
public class Light extends Component {
    static {
        register(Light.class);
    }
    private  PointLight pointLight;

    public Light(Integer rays,Float distance,Integer x,Integer y){
        pointLight = new PointLight(Engine.getInstance().getRayHandler(),rays, new Color(165,44,52,1),distance,x,y);
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
	@Override
	public Component interpolateImpl(Component other, float perc) {
		// TODO Auto-generated method stub
		return null;
	}
}

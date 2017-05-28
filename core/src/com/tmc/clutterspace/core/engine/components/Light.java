package com.tmc.clutterspace.core.engine.components;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import java.nio.ByteBuffer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tmc.clutterspace.core.engine.Engine;
import com.tmc.clutterspace.core.engine.State;
import com.tmc.clutterspace.core.utility.AssetLoader;

/**
 * Created by emanuel on 27.05.2017.
 */
public class Light extends Component {
    static {
        register(Light.class);
    }
    private PointLight pointLight;

    public Light(float distance){
    	getDependencies().add(Transform2D.class);
        pointLight = new PointLight(Engine.getInstance().getRayHandler(), 500);
        pointLight.setDistance(distance);
        pointLight.setXray(true);
    }
    
    public Light(float distance, boolean dummy){
    	getDependencies().add(Transform2D.class);
        pointLight = new PointLight(Engine.getInstance().getRayHandler(), 500);
        pointLight.setDistance(distance);
        pointLight.setXray(true);
        if(dummy)pointLight.remove();
    }
    
    public PointLight getPointLight(){
        return pointLight;
    }

    @Override
    protected void initImpl() {
    	Transform2D trans = getGameObject().getComponent(Transform2D.class);
    	pointLight.setPosition(trans.p);

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
    	Transform2D trans = getGameObject().getComponent(Transform2D.class);
    	pointLight.setPosition(trans.p);

    }

    @Override
    protected void onGuiImpl(SpriteBatch batch) {

    }

    @Override
	public State getState() {
		ByteBuffer buf = ByteBuffer.allocate(8 * 4);
		State s =  new State(this);
		buf.putFloat(pointLight.getDistance());
		buf.putInt(pointLight.getColor().toIntBits());
		s.values = buf.array();
		
		return s;
	}
	
	
	public static Component fromState(State state) {
		ByteBuffer buf = ByteBuffer.wrap(state.values);
		Light comp = new Light(buf.getFloat(), true);
		comp.pointLight.setColor(new Color(buf.getInt()));
		return comp;
	}
	
	@Override
	public Component interpolateImpl(Component other, float perc) {
		Light oth = (Light) other;
		Light comp = new Light(oth.pointLight.getDistance());

		comp.pointLight.setColor(oth.pointLight.getColor());
		return comp;
	}

	@Override
	protected void preRenderImpl(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		pointLight.dispose();
	}
}

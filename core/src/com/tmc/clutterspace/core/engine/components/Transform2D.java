package com.tmc.clutterspace.core.engine.components;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tmc.clutterspace.core.engine.State;

/**
 * Holds position and rotation used for rendering, pshysics etc.
 * <p>
 * See {@link #Transform2D(Vector2)} for more information.
 * @author roadd
 *
 */
public class Transform2D extends Component{
	static{
		register(Transform2D.class);
	}
	
	/**
	   * Position vector({@link Vector2D} used for rendering.
	   */
	public Vector2 p;
	
	/**
	   * Rotation angle in degrees used for rendering.
	   */
	public float a;
	
	/**
	   * Constructor for the {@link Transform2D} component.
	   * @param p The initial position vector.
	   */
	public Transform2D(Vector2 p) {
		this.p = p.cpy();
	}
	
	/**
	 *  Constructor for the {@link Transform2D} component.
	 * @param x The initial x-axis position.
	 * @param y The initial y-axis position.
	 */
	public Transform2D(float x, float y) {
		this.p = new Vector2(x, y);
	} 

	@Override
	protected void updateImpl(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void renderImpl(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initImpl() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void prepareImpl() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onGuiImpl(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void postUpdateImpl() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State getState() {
		ByteBuffer buf = ByteBuffer.allocate(12);
		State s =  new State(this);
		buf.putFloat(p.x);
		buf.putFloat(p.y);
		buf.putFloat(a);
		s.values = buf.array();
		
		return s;
	}
	
	public static Component fromState(State s){
		ByteBuffer buf = ByteBuffer.wrap(s.values);
		Transform2D comp = new Transform2D(buf.getFloat(), buf.getFloat());
		comp.a = buf.getFloat();
		return comp;
	}

	@Override
	public Component interpolateImpl(Component other, float perc) {
		// TODO Auto-generated method stub
		Transform2D oth = (Transform2D) other;
		Transform2D ret = new Transform2D(this.p.x * (1 - perc) + oth.p.x * perc, this.p.y * (1 - perc) + oth.p.y * perc);
		ret.a = this.a * (1 - perc) + oth.a * perc;
		return ret;
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

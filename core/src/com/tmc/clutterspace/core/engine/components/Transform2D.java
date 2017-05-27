package com.tmc.clutterspace.core.engine.components;

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
public class Transform2D extends Component {
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
		// TODO Auto-generated method stub
		State s =  new State(this);
		s.values.add(1);
		return s;
	}
	

}

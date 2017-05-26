package com.tmc.clutterspace.core.components;

import com.badlogic.gdx.math.Vector2;

/**
 * Holds position and rotation used for rendering, pshysics etc.
 * <p>
 * See {@link #Transform2D(Vector2)} for more information.
 * @author roadd
 *
 */
public class Transform2D extends Component {
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

	@Override
	protected void updateImpl(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void renderImpl() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initImpl() {
		// TODO Auto-generated method stub
		
	}
	

}

package com.tmc.clutterspace.core.components;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Wrapper coomponent for libgdx {@link Body}.
 * <p>
 * Component is dependent on {@link Transform2D}.
 * <p>
 * See {@link #Body2D(World, BodyType)} for more information.
 * @author roadd
 *
 */
public class Body2D extends Component {
	private final BodyDef bodyDef;
	private Body body = null;
	private final World world;
	
	/**
	   * Constructor for the {@link Body2D} component.
	   * <p>
	   * Component is dependent on {@link Transform2D}
	   * @param w The world that this body is linked to.
	   * @param type The type of body that will be created.
	   */
	public Body2D(World w, BodyType type){
		this.dependencies.add(Transform2D.class);
		
		bodyDef = new BodyDef();
	    bodyDef.type = BodyType.DynamicBody;
	    world = w;
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
	    bodyDef.position.set(this.gameObject.getComponent(Transform2D.class).p);

        body = world.createBody(bodyDef);
	}

	/**
	 * Returns the libgdx {@link Body}. 
	 * @return The physics body.
	 */
	public Body getBody() {
		return body;
	}
}

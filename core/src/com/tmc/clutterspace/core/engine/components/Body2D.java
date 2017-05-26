package com.tmc.clutterspace.core.engine.components;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.tmc.clutterspace.core.engine.State;

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
		this.getDependencies().add(Transform2D.class);
		
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

	@Override
	protected void prepareImpl() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void postUpdateImpl() {
		gameObject.getComponent(Transform2D.class).p = body.getPosition();
	}

	@Override
	protected void onGuiImpl() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Returns the libgdx {@link Body}. 
	 * @return The physics body.
	 */
	public Body getBody() {
		return body;
	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

}

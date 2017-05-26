package com.tmc.clutterspace.core.engine.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tmc.clutterspace.core.engine.State;
import com.tmc.clutterspace.core.utility.AssetLoader;

/**
 * Wrapper component for libgdx {@link Sprite}.
 * <p>
 * See {@link #Sprite2D(String)} for more information.
 * @author roadd
 *
 */
public class Sprite2D extends Component {
	public Sprite sprite; 
	
	/**
	 * Constructor for {@link Sprite2D} component.
	 * @param name The asset name/path.
	 */
	public Sprite2D(String name){
		Texture tex = AssetLoader.get(name, Texture.class);
		sprite = new Sprite(tex);
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

	@Override
	protected void prepareImpl() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onGuiImpl() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void postUpdateImpl() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

}

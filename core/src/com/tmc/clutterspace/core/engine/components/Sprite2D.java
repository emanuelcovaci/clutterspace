package com.tmc.clutterspace.core.engine.components;

import java.nio.ByteBuffer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
	static{
		register(Sprite2D.class);
	}
	
	public Texture tex;
	private String texName;
	private boolean rotate = false;
	public Vector2 offset = new Vector2(0, 0); 
	public Vector2 size = new Vector2(100, 100);
	private boolean flippedX = false, flippedY = false;
	/**
	 * Constructor for {@link Sprite2D} component.
	 * @param name The asset name/path.
	 */
	public Sprite2D(String name){
		this.getDependencies().add(Transform2D.class);
		tex = AssetLoader.get(name, Texture.class);
		texName = name;
	}
	
	@Override
	protected void updateImpl(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void renderImpl(SpriteBatch batch) {
		Transform2D trans = getGameObject().getComponent(Transform2D.class);
		batch.draw(tex, trans.p.x + offset.x, trans.p.y + offset.y, offset.x, offset.y, size.x, size.y, 1, 1, rotate ? trans.a : 0,  0, 0, tex.getWidth(), tex.getHeight(), flippedX, flippedY);
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
		ByteBuffer buf = ByteBuffer.allocate(8 * 4);
		State s =  new State(this);
		buf.putInt(AssetLoader.Dictionary.get(texName));
		buf.put((byte) (rotate ? 1 : 0));
		buf.putFloat(offset.x);
		buf.putFloat(offset.y);
		buf.putFloat(size.x);
		buf.putFloat(size.y);
		buf.put((byte) (flippedX ? 1 : 0));
		buf.put((byte) (flippedY ? 1 : 0));
		s.values = buf.array();
		
		return s;
	}
	
	
	public static Component fromState(State state) {
		ByteBuffer buf = ByteBuffer.wrap(state.values);
		Sprite2D comp = new Sprite2D(AssetLoader.Dictionary.inverse().get(buf.getInt()));
		comp.rotate = buf.get() == 1 ? true : false;
		comp.offset = new Vector2(buf.getFloat(), buf.getFloat());
		comp.size = new Vector2(buf.getFloat(), buf.getFloat());
		comp.flippedX = buf.get() == 1 ? true : false;
		comp.flippedY = buf.get() == 1 ? true : false;
		return comp;
	}

	/**
	 * Returns if the sprite should be drawned rotated.
	 * @return {@link true} if the sprite should be drawned rotated.
	 */
	public boolean shouldRotate() {
		return rotate;
	}

	/**
	 * Sets if the sprite should be drawned rotated.
	 */
	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}
	
	/**
	 * Sets the x-axis scale.
	 */
	public void setSizeX(float x){
		size.x = x;
	}
	
	/**
	 * Sets the y-axis scale.
	 */
	public void setSizeY(float y){
		size.y = y;
	}
	
	/**
	 * Sets the x-axis offset.
	 */
	public void setOffsetX(float x){
		offset.x = x;
	}
	
	/**
	 * Sets the y-axis offset.
	 */
	public void setOffsetY(float y){
		offset.y = y;
	}

	/**
	 * Returns if the sprite should be flipped on the x-axis.
	 * @return {@link true} if the sprite should be flipped on the x-axis.
	 */
	public boolean isFlipedX() {
		return flippedX;
	}

	/**
	 * Sets if the sprite should be flipped on the x-axis.
	 */
	public void setFlippedX(boolean flipped) {
		this.flippedX = flipped;
	}
	
	/**
	 * Returns if the sprite should be flipped on the y-axis.
	 * @return {@link true} if the sprite should be flipped on the y-axis.
	 */
	public boolean isFlipedY() {
		return flippedY;
	}

	/**
	 * Sets if the sprite should be flipped on the y-axis.
	 */
	public void setFlippedY(boolean flipped) {
		this.flippedY = flipped;
	}

	@Override
	public Component interpolateImpl(Component other, float perc) {
		return other;
	}
}

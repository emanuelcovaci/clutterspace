package com.tmc.clutterspace.core.engine.components;

import java.nio.ByteBuffer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tmc.clutterspace.core.engine.Engine;
import com.tmc.clutterspace.core.engine.State;
import com.tmc.clutterspace.core.utility.AssetLoader;

public class TileMapBackground extends Component {
	static{
		register(TileMapBackground.class);
	}
	
	private int[] bgLayers;
	
	public TileMapBackground(int[] bgLayers){
		getDependencies().add(TileMap.class);
		this.bgLayers = bgLayers;
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
	protected void updateImpl(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void postUpdateImpl() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void renderImpl(SpriteBatch batch) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onGuiImpl(SpriteBatch batch) {
		// TODO Auto-generated method stub

	}

	@Override
	public State getState() {
		ByteBuffer buf = ByteBuffer.allocate(4 * bgLayers.length);
		State s = new State(this);
		for(int i = 0; i < bgLayers.length; i++)
			buf.putInt(bgLayers[i]);
		s.values = buf.array();
		
		return s;
	}
	
	public static Component fromState(State s){
		ByteBuffer buf = ByteBuffer.wrap(s.values);
		int[] bgLayers = new int[s.values.length / 4];
		for(int i = 0; i < s.values.length / 4; i++)
			bgLayers[i] = buf.getInt();
		return new TileMapBackground(bgLayers);
	}

	@Override
	public Component interpolateImpl(Component other, float perc) {
		TileMapBackground oth = (TileMapBackground) other;
		return new TileMapBackground(oth.bgLayers);
	}

	@Override
	protected void preRenderImpl(SpriteBatch batch) {
		OrthographicCamera cam = Engine.getInstance().getCamera();
		cam.update();
		System.out.println("sadasd");
		getGameObject().getComponent(TileMap.class).renderer.setView(cam);
		getGameObject().getComponent(TileMap.class).renderer.render(bgLayers);
	}

	public int[] getBgLayers() {
		return bgLayers;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}

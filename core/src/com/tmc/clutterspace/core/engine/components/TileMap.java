package com.tmc.clutterspace.core.engine.components;

import java.nio.ByteBuffer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.tmc.clutterspace.core.engine.Engine;
import com.tmc.clutterspace.core.engine.State;
import com.tmc.clutterspace.core.utility.AssetLoader;

public class TileMap extends Component {
	static{
		register(TileMap.class);
	}
	
	
	private TiledMap map;
	protected String mapName;
	public OrthogonalTiledMapRenderer renderer;
	public TileMap(String name){
		map = AssetLoader.get(name, TiledMap.class);
		mapName = name;
		renderer = new OrthogonalTiledMapRenderer(map);
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
		ByteBuffer buf = ByteBuffer.allocate(4);
		State s = new State(this);
		buf.putInt(AssetLoader.Dictionary.get(mapName));
		s.values = buf.array();
		
		return s;
	}
	
	public static Component fromState(State s){
		ByteBuffer buf = ByteBuffer.wrap(s.values);
		TileMap comp = new TileMap(AssetLoader.Dictionary.inverse().get(buf.getInt()));
		return comp;
	}

	@Override
	public Component interpolateImpl(Component other, float perc) {
		TileMap oth = (TileMap) other;
		return new TileMap(this.mapName);
	}

	public TiledMap getMap() {
		return map;
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

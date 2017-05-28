package com.tmc.clutterspace.core.engine.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tmc.clutterspace.core.collision.CollisionDict;
import com.tmc.clutterspace.core.engine.State;

public class TileMapShapes extends Component {
	protected int[] colLayers;
	
	public TileMapShapes(int[] colLayers){
		getDependencies().add(TileMap.class);
		getDependencies().add(Body2D.class);
		this.colLayers = colLayers;
	}
	
	@Override
	protected void initImpl() {
		Body2D body = getGameObject().getComponent(Body2D.class);
		body.init();
		TileMap map = getGameObject().getComponent(TileMap.class);
		for(int i = 0; i < colLayers.length; i++){
			System.out.println( map.getMap().getLayers().get(colLayers[i]).getName());
			MapObjects objs = map.getMap().getLayers().get(colLayers[i]).getObjects();
			for(MapObject obj : objs){
				PolygonMapObject polyObj = (PolygonMapObject) obj;
				Polygon poly = polyObj.getPolygon();
				PolygonShape polyShape = new PolygonShape();
				float[] vert = poly.getTransformedVertices();
				polyShape.set(vert);
				FixtureDef fixtureDef = new FixtureDef();
				fixtureDef.shape = polyShape;
		        fixtureDef.filter.categoryBits = CollisionDict.CATEGORY_GROUND;
		        fixtureDef.filter.maskBits = CollisionDict.MASK_GROUND;
				Fixture fix = body.getBody().createFixture(fixtureDef);
				fix.setUserData(getGameObject());
			}
//			PolygonShape shape = new PolygonShape();
//	        rockBox2.setAsBox(30, 5.0f);
//	        fixture = rock2.getComponent(Body2D.class).getBody().createFixture(rockBox2, 0.0f);
//	        fixture.setUserData(rock2);
		}

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
	protected void preRenderImpl(SpriteBatch batch) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component interpolateImpl(Component other, float perc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}

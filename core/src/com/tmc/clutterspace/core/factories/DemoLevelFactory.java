package com.tmc.clutterspace.core.factories;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tmc.clutterspace.core.engine.Engine;
import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.components.Body2D;
import com.tmc.clutterspace.core.engine.components.TileMap;
import com.tmc.clutterspace.core.engine.components.TileMapBackground;
import com.tmc.clutterspace.core.engine.components.TileMapShapes;
import com.tmc.clutterspace.core.engine.components.Transform2D;

public class DemoLevelFactory {
	public static void create(){
		GameObject map = new GameObject();
		map.setComponent(new TileMap("levels/demo.tmx"));
		map.setComponent(new TileMapBackground(new int[]{0,1,2}));
		map.setComponent(new Transform2D(0, 0));
		map.setComponent(new Body2D(BodyType.StaticBody));
		map.setComponent(new TileMapShapes(new int[]{3}));
		
		map.init();
		Engine.getInstance().addEntities(map);
		
	}
}

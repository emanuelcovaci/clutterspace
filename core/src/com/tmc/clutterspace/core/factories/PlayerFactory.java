package com.tmc.clutterspace.core.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.components.Body2D;
import com.tmc.clutterspace.core.engine.components.GroundSensor;
import com.tmc.clutterspace.core.engine.components.Health;
import com.tmc.clutterspace.core.engine.components.Player;
import com.tmc.clutterspace.core.engine.components.Sprite2D;
import com.tmc.clutterspace.core.engine.components.Transform2D;

public class PlayerFactory {
	public static GameObject create(){
		GameObject player = new GameObject();
		player.setComponent(new Transform2D(100, 300));
		player.setComponent(new Body2D(BodyType.DynamicBody, true));
		player.setComponent(new Health());
		player.setComponent(new Sprite2D("Viking.png"));
		player.getComponent(Sprite2D.class).size = new Vector2(30, 40);
		player.getComponent(Sprite2D.class).offset = new Vector2(-15, -20);
		player.setComponent(new GroundSensor());
		player.setComponent(new Player());
		
		player.init();
        
		return player;
	}
}

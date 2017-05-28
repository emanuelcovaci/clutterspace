package com.tmc.clutterspace.core.factories;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tmc.clutterspace.core.collision.CollisionDict;
import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.components.Body2D;
import com.tmc.clutterspace.core.engine.components.Projectile;
import com.tmc.clutterspace.core.engine.components.Sprite2D;
import com.tmc.clutterspace.core.engine.components.Transform2D;

public class CornFactory {
	public static GameObject create(Vector2 pos, Vector2 dir){
		GameObject corn = new GameObject();
		corn.setComponent(new Transform2D(pos));
		Random rand = new Random();
		corn.setComponent(new Body2D(BodyType.KinematicBody));
		corn.setComponent(new Sprite2D("corn_projectile.png"));
		corn.getComponent(Sprite2D.class).size = new Vector2(40, 40);
		corn.getComponent(Sprite2D.class).offset = new Vector2(-20, -20);
		corn.setComponent(new Projectile(5, dir.rotate(rand.nextFloat() * 60 - 30)));

		corn.init();
		
		CircleShape circle = new CircleShape();
        circle.setRadius(20f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.filter.categoryBits = CollisionDict.CATEGORY_PROJECTILE;
        fixtureDef.filter.maskBits = CollisionDict.MASK_PROJECTILE;
//        fixtureDef.isSensor = true;
        Fixture fixture = corn.getComponent(Body2D.class).getBody().createFixture(fixtureDef);
        fixture.setUserData(corn);
		
		
		return corn;
	}
}

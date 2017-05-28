//package com.tmc.clutterspace.core.factories;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import com.badlogic.gdx.physics.box2d.CircleShape;
//import com.badlogic.gdx.physics.box2d.Fixture;
//import com.badlogic.gdx.physics.box2d.FixtureDef;
//import com.tmc.clutterspace.core.engine.Engine;
//import com.tmc.clutterspace.core.engine.GameObject;
//import com.tmc.clutterspace.core.engine.components.*;
//
///**
// * Created by emanuel on 27.05.2017.
// */
//public class LionFactory {
//    public static GameObject create() {
//        GameObject lion = new GameObject();
//        lion.setComponent(new Transform2D(100, 300));
//        lion.setComponent(new Body2D(BodyDef.BodyType.DynamicBody));
//        lion.setComponent(new Health());
//        lion.setComponent(new Sprite2D("lion.png"));
//        lion.getComponent(Sprite2D.class).size = new Vector2(100, 100);
//        lion.getComponent(Sprite2D.class).offset = new Vector2(-50, -50);
//
//        lion.setComponent(new Control());
//
//        lion.init();
//
//        CircleShape circle = new CircleShape();
//        circle.setRadius(50f);
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = circle;
//        fixtureDef.density = 0.5f;
//        fixtureDef.friction = 0.01f;
//        fixtureDef.restitution = 0.6f;
//        Fixture fixture = lion.getComponent(Body2D.class).getBody().createFixture(fixtureDef);
//        fixture.setUserData(lion);
//
//        return lion;
//    }
//
//}

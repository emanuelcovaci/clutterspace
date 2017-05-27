package com.tmc.clutterspace.core;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tmc.clutterspace.core.engine.Engine;
import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.components.Body2D;
import com.tmc.clutterspace.core.engine.components.Component;
import com.tmc.clutterspace.core.engine.components.Control;
import com.tmc.clutterspace.core.engine.components.Sprite2D;
import com.tmc.clutterspace.core.engine.components.Transform2D;
import com.tmc.clutterspace.core.utility.AssetLoader;

/**
 * Created by emanuel on 20.05.2017.
 */
public class FirstScreen implements Screen {
    final Main game;
    Engine en;


    private Music music_level1;
    Vector2 vec = new Vector2(0,0);

    public  FirstScreen(final Main game){

        this.game = game;
        
        en = Engine.getInstance();
        en.debug = true;
        en.step = 2/60f;
        
        en.getWorld().setGravity(new Vector2(0, -10));
        
        GameObject lion = new GameObject();
        lion.setComponent(new Transform2D(100, 300));
        lion.setComponent(new Body2D(BodyType.DynamicBody));
        
        
        lion.setComponent(new Sprite2D("lion.png"));
        lion.getComponent(Sprite2D.class).size = new Vector2(100, 100);
        lion.getComponent(Sprite2D.class).offset = new Vector2(-50, -50);

        lion.setComponent(new Control());

        lion.init();
        en.addEntities(lion);

        CircleShape circle = new CircleShape();
        circle.setRadius(50f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 2f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.6f;
        Fixture fixture = lion.getComponent(Body2D.class).getBody().createFixture(fixtureDef);
        fixture.setUserData(lion);
        
        GameObject floor = new GameObject();
        floor.setComponent(new Transform2D(0, 90));
        floor.setComponent(new Body2D(BodyType.StaticBody));

        floor.init();
        en.addEntities(floor);
        

        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(en.getCamera().viewportWidth, 10.0f);
        fixture = floor.getComponent(Body2D.class).getBody().createFixture(groundBox, 0.0f);
        fixture.setUserData(floor);

        GameObject background = new GameObject();
        background.setComponent(new Transform2D(0, 0));
        background.setComponent(new Body2D(BodyType.StaticBody));
        background.setComponent(new Sprite2D("background.jpg"));
        background.getComponent(Sprite2D.class).size = new Vector2(en.getCamera().viewportWidth, en.getCamera().viewportHeight);
        background.getComponent(Sprite2D.class).offset = new Vector2(0, 0);

        background.init();
        en.addEntities(background);

        new PointLight(en.getRayHandler(),5000,Color.CYAN,1000,700,500);

        music_level1 = AssetLoader.get("background.mp3", Music.class);

		System.out.println(Component.Dictionary);
        try {
			System.out.println(en.decodeSnapshots(en.createSnapshot()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
    public void render(float v) {
    	
    	while(v > 0){
    		en.update();
    		v -= en.step / 5;
    	}
    	en.render();
        music_level1.setLooping(true);
        music_level1.play();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

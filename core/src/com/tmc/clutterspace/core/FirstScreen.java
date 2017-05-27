package com.tmc.clutterspace.core;

import java.io.IOException;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tmc.clutterspace.core.engine.Engine;
import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.components.Body2D;
import com.tmc.clutterspace.core.engine.components.Component;
import com.tmc.clutterspace.core.engine.components.Control;
import com.tmc.clutterspace.core.engine.components.Health;
import com.tmc.clutterspace.core.engine.components.Light;
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


    public  FirstScreen(final Main game){

        this.game = game;
        
        en = Engine.getInstance();
        en.debug = true;
        en.step = 2/60f;
        
        en.getWorld().setGravity(new Vector2(0, -10));
        
        GameObject lion = new GameObject();
        lion.setComponent(new Transform2D(100, 300));
        lion.setComponent(new Body2D(BodyType.DynamicBody));
        lion.setComponent(new Health());
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
        background.setComponent(new Sprite2D("background01.png"));
        background.getComponent(Sprite2D.class).size = new Vector2(en.getCamera().viewportWidth, en.getCamera().viewportHeight);

//        rock = new GameObject();
//        rock.setComponent(new Transform2D(125,295));
//        rock.setComponent(new Body2D(w, BodyType.StaticBody));
//
//        rock.init();
//
//        PolygonShape rockBox = new PolygonShape();
//        rockBox.setAsBox(30, 5.0f);
//        fixture = rock.getComponent(Body2D.class).getBody().createFixture(rockBox, 0.0f);
//        fixture.setUserData(rock);
//
//        rock2 = new GameObject();
//        rock2.setComponent(new Transform2D(470,495));
//        rock2.setComponent(new Body2D(w, BodyType.StaticBody));
//
//        rock2.init();
//
//        PolygonShape rockBox2 = new PolygonShape();
//        rockBox2.setAsBox(30, 5.0f);
//        fixture = rock2.getComponent(Body2D.class).getBody().createFixture(rockBox2, 0.0f);
//        fixture.setUserData(rock2);
//
//
//        rock3 = new GameObject();
//        rock3.setComponent(new Transform2D(600,280));
//        rock3.setComponent(new Body2D(w, BodyType.StaticBody));
//
//        rock3.init();
//
//        PolygonShape rockBox3 = new PolygonShape();
//        rockBox3.setAsBox(30, 5.0f);
//        fixture = rock3.getComponent(Body2D.class).getBody().createFixture(rockBox3, 0.0f);
//        fixture.setUserData(rock3);


        background.getComponent(Sprite2D.class).offset = new Vector2(0, 0);

        background.init();
        en.addEntities(background);

        GameObject light = new GameObject();
        light.setComponent(new Light(500,300f,300,531));
        
        light.init();
        en.addEntities(light);

        GameObject light2 = new GameObject();
        light2.setComponent(new Light(500,300f,1128,891));

        light2.init();
        en.addEntities(light2);

        GameObject light3 = new GameObject();
        light3.setComponent(new Light(500,300f,1440,504));
        

        light3.init();
        en.addEntities(light3);

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

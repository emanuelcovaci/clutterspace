package com.tmc.clutterspace.core;

import box2dLight.PointLight;
import box2dLight.RayHandler;
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
import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.components.*;
import com.tmc.clutterspace.core.utility.AssetLoader;

/**
 * Created by emanuel on 20.05.2017.
 */
public class FirstScreen implements Screen {
    final Main game;



    private Music music_level1;
    Vector2 vec = new Vector2(0,0);
    World w;
    GameObject lion, floor,background,light;
    Box2DDebugRenderer debugRenderer;
    RayHandler rayHandler;

    public  FirstScreen(final Main game){

        this.game = game;
        
        w = new World(new Vector2(0, -100), true);
        
        lion = new GameObject();
        lion.setComponent(new Transform2D(100, 300));
        lion.setComponent(new Body2D(w, BodyType.DynamicBody));
        
        
        lion.setComponent(new Sprite2D("lion.png"));
        lion.getComponent(Sprite2D.class).size = new Vector2(100, 100);
        lion.getComponent(Sprite2D.class).offset = new Vector2(-50, -50);

        lion.setComponent(new Control());

        lion.init();
        

        CircleShape circle = new CircleShape();
        circle.setRadius(50f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.01f;
        fixtureDef.restitution = 0.6f;
        Fixture fixture = lion.getComponent(Body2D.class).getBody().createFixture(fixtureDef);
        fixture.setUserData(lion);
        
        floor = new GameObject();
        floor.setComponent(new Transform2D(0, 0));
        floor.setComponent(new Body2D(w, BodyType.StaticBody));
        
        floor.init();
        

        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(game.cam.viewportWidth, 10.0f);
        fixture = floor.getComponent(Body2D.class).getBody().createFixture(groundBox, 0.0f);
        fixture.setUserData(floor);

        background = new GameObject();
        background.setComponent(new Transform2D(0, 0));
        background.setComponent(new Body2D(w, BodyType.StaticBody));
        background.setComponent(new Sprite2D("background01.png"));
        background.getComponent(Sprite2D.class).size = new Vector2(800, 600);
        background.getComponent(Sprite2D.class).offset = new Vector2(0, 0);

        background.init();

        rayHandler = new RayHandler(w);
        rayHandler.setCombinedMatrix(game.cam);
        rayHandler.setShadows(false);


        light = new GameObject();
        light.setComponent(new Light(rayHandler,500,1000f,700,500));
        

        debugRenderer = new Box2DDebugRenderer();


        music_level1 = AssetLoader.get("background.mp3", Music.class);
    }
    @Override
    public void render(float v) {
        background.prepare();
    	lion.prepare();
    	floor.prepare();
    	lion.update(5/60f);
    	floor.update(5/60f);
    	lion.postUpdate();
    	floor.postUpdate();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        music_level1.setLooping(true);
        music_level1.play();
        w.step(5/60f, 6, 2);

        game.batch.setProjectionMatrix(game.cam.combined);
        game.batch.begin();



        background.render(game.batch);
        lion.render(game.batch);
    	floor.render(game.batch);
        lion.onGui(game.batch);
    	floor.render(game.batch);


        game.batch.end();
        rayHandler.updateAndRender();

        debugRenderer.render(w, game.cam.combined);
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

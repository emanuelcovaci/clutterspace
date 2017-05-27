package com.tmc.clutterspace.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tmc.clutterspace.core.utility.AssetLoader;

/**
 * Created by emanuel on 20.05.2017.
 */
public class FirstScreen implements Screen {
    final Main game;

    Texture texture,texture2,backgroundTexture;
    private Sprite sprite ,sprite2;
    private Music music_level1;
    Vector2 vec = new Vector2(0,0);
    World w;
    Body b;
    Box2DDebugRenderer debugRenderer;

    public  FirstScreen(final Main game){

        this.game = game;

        w = new World(new Vector2(0, -100), true);
        debugRenderer = new Box2DDebugRenderer();
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(100, 300);

        b = w.createBody(bodyDef);

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 90));
        Body groundBody = w.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(game.cam.viewportWidth, 10.0f);
        groundBody.createFixture(groundBox, 0.0f);



        backgroundTexture = AssetLoader.get("background.jpg", Texture.class);
        texture = AssetLoader.get("lion.png", Texture.class);
        texture2 = AssetLoader.get("pidgey.png", Texture.class);
        music_level1 = AssetLoader.get("background.mp3", Music.class);
        sprite = new Sprite(texture);
        sprite2 = new Sprite(texture2);

//        float scale = 20f / sprite.getHeight();
//        sprite.setScale(scale);
        sprite.setBounds(0, 0, 20, 20);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);
        sprite.setPosition(b.getPosition().x-sprite.getOriginX(),b.getPosition().y-sprite.getOriginY());
        sprite.setOriginCenter();



        System.out.println(sprite.getBoundingRectangle());


        CircleShape circle = new CircleShape();
        circle.setRadius(10f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 2f;
        fixtureDef.friction = 0.01f;
        fixtureDef.restitution = 0.6f;
        Fixture fixture = b.createFixture(fixtureDef);
        fixture.setUserData(b);





    }
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
//        music_level1.setLooping(true);
//        music_level1.play();
        w.step(20/60f, 6, 2);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            b.applyForceToCenter(-100000,0,true);

//            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
//                sprite.translateX(-1f);
//            else
//                sprite.translateX(-10.0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            b.applyForceToCenter(100000,0,true);


//            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
//                sprite.translateX(1f);
//            else
//                sprite.translateX(10.0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            b.applyForceToCenter(0,100000,true);
        }
//            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
//                sprite.translateY(1f);
//            else
//                sprite.translateY(10.0f);
//        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            b.applyForceToCenter(0,-100000,true);
        }
//            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
//                sprite.translateY(-1f);
//            else
//                sprite.translateY(-10.0f);
//        }
        if(Gdx.input.isKeyPressed(Input.Keys.R)){
            b.setTransform(50,50,50);

        }

//        if(Gdx.input.isKeyPressed(Input.Keys.A)){
//            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
//                sprite2.translateX(-1f);
//            else
//                sprite2.translateX(-10.0f);
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.D)){
//            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
//                sprite2.translateX(1f);
//            else
//                sprite2.translateX(10.0f);
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.W)){
//            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
//                sprite2.translateY(1f);
//            else
//                sprite2.translateY(10.0f);
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.S)){
//            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
//                sprite2.translateY(-1f);
//            else
//                sprite2.translateY(-10.0f);
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.E)){
//            sprite2.setPosition(50, 50);
//        }
//
//
        game.batch.setProjectionMatrix(game.cam.combined);
        game.batch.begin();



        game.batch.draw(backgroundTexture, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());

        sprite.setPosition(b.getPosition().x,b.getPosition().y);
        sprite.draw(game.batch);
//        sprite2.draw(game.batch);

        game.batch.end();
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

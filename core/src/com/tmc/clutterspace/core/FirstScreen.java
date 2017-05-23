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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

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

    public  FirstScreen(final Main game){

        this.game = game;
        
        w = new World(new Vector2(0, -1), true);
        
        BodyDef bodyDef = new BodyDef();
	    // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
	    bodyDef.type = BodyType.DynamicBody;
	    // Set our body's starting position in the world
	    bodyDef.position.set(100, 300);

        b = w.createBody(bodyDef);
        
        this.game.vec = b.getPosition();
        System.out.println(Gdx.files.internal("background.jpg"));
        backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));
        texture = new Texture(Gdx.files.internal("lion.png"));
        texture2 = new Texture(Gdx.files.internal("pidgey.png"));
        music_level1 = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
        sprite = new Sprite(texture);
        sprite2 = new Sprite(texture2);
    }
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        music_level1.setLooping(true);
        music_level1.play();
        w.step(1/60f, 6, 2);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
                sprite.translateX(-1f);
            else
                sprite.translateX(-10.0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
                sprite.translateX(1f);
            else
                sprite.translateX(10.0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
                sprite.translateY(1f);
            else
                sprite.translateY(10.0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
                sprite.translateY(-1f);
            else
                sprite.translateY(-10.0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.R)){
            sprite.setPosition(50, 50);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
                sprite2.translateX(-1f);
            else
                sprite2.translateX(-10.0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
                sprite2.translateX(1f);
            else
                sprite2.translateX(10.0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
                sprite2.translateY(1f);
            else
                sprite2.translateY(10.0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
                sprite2.translateY(-1f);
            else
                sprite2.translateY(-10.0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.E)){
            sprite2.setPosition(50, 50);
        }

        game.batch.setProjectionMatrix(game.cam.combined);
        game.batch.begin();


        game.batch.draw(backgroundTexture, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());

        sprite.draw(game.batch);
        sprite2.draw(game.batch);
        game.batch.end();
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

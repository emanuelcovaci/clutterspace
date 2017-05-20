package com.tmc.clutterspace.core;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

public class Main implements ApplicationListener {
	Texture texture,texture2;
	SpriteBatch batch;
	float elapsed;
	private Sprite sprite ,sprite2;

	@Override
	public void create () {
		texture = new Texture(Gdx.files.internal("lion.png"));
		texture2 = new Texture(Gdx.files.internal("pidgey.png"));
		batch = new SpriteBatch();
		sprite = new Sprite(texture);
		sprite2 = new Sprite(texture2);


	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

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

		batch.begin();

		sprite.draw(batch);
        sprite2.draw(batch);
		batch.end();
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}

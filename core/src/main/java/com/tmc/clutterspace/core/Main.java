package com.tmc.clutterspace.core;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class Main extends Game {
	SpriteBatch batch;
    OrthographicCamera cam;
    Viewport viewport;

	@Override
	public void create () {
        cam = new OrthographicCamera(800, 600);
        viewport = new StretchViewport(800, 600, cam);
	    batch = new SpriteBatch();
	    resize(800, 600);
        this.setScreen(new FirstScreen(this));
	}

	public void resize(int w, int h){
        if (!Gdx.app.getType().equals(Application.ApplicationType.Android)) {
            Gdx.graphics.setDisplayMode(w, h, false);
            viewport.update(w, h, true);
        } else {
            Gdx.graphics.setDisplayMode(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true);
        }
    }

	public void render() {
	    super.render();

	}

    public void dispose() {
        batch.dispose();
    }
}

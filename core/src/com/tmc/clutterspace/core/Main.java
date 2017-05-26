package com.tmc.clutterspace.core;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tmc.clutterspace.core.utility.AssetLoader;

public class Main extends Game {
	SpriteBatch batch;
    OrthographicCamera cam;
    Viewport viewport;
    Vector2 vec = new Vector2(0, 0);

	@Override
	public void create () {
		AssetLoader loader = AssetLoader.getInstance();
		loader.assets.finishLoading();
		
        cam = new OrthographicCamera(800, 600);
        viewport = new StretchViewport(800, 600, cam);
	    batch = new SpriteBatch();
	    resize(800, 600);
        this.setScreen(new FirstScreen(this));
	}

	public void resize(int w, int h){
        viewport.update(w, h, true);
    }

	public void render() {
		System.out.println(vec);
	    super.render();

	}

    public void dispose() {
        batch.dispose();
    }
}

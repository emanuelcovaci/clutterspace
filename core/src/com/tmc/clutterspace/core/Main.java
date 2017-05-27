package com.tmc.clutterspace.core;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.State;
import com.tmc.clutterspace.core.engine.components.Transform2D;
import com.tmc.clutterspace.core.utility.AssetLoader;

import java.io.IOException;

public class Main extends Game {
	SpriteBatch batch;
    OrthographicCamera cam;
    Viewport viewport;
    Vector2 vec = new Vector2(0, 0);

	@Override
	public void create () {
		AssetLoader loader = AssetLoader.getInstance();
		loader.assets.finishLoading();

		GameObject obj = new GameObject();
		obj.setComponent(new Transform2D(new Vector2(2, 3)));
		try {
			System.out.println(obj.serialize().length);
			ArrayList<State> a = GameObject.deserialize(obj.serialize());
			System.out.println(a.get(0).typeId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	    super.render();

	}

    public void dispose() {
        batch.dispose();
    }
}

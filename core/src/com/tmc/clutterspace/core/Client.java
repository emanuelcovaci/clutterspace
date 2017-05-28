package com.tmc.clutterspace.core;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Vector2;
import com.tmc.clutterspace.core.engine.components.Component;
import com.tmc.clutterspace.core.utility.AssetLoader;

import java.io.IOException;

public class Client extends Game {
    Vector2 vec = new Vector2(0, 0);

	@Override
	public void create() {
		AssetLoader loader = AssetLoader.getInstance();
		loader.assets.finishLoading();

        this.setScreen(new FirstScreen(this, false));
	}

	public void resize(int w, int h){
		
    }

	public void render() {
	    super.render();

	}

    public void dispose() {
    }
}

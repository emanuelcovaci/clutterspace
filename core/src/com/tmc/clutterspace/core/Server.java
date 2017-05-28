package com.tmc.clutterspace.core;

import com.badlogic.gdx.Game;
import com.tmc.clutterspace.core.utility.AssetLoader;

public class Server extends Game {

	@Override
	public void create() {
		AssetLoader loader = AssetLoader.getInstance();
		loader.assets.finishLoading();

        this.setScreen(new FirstScreen(this, true));
	}
	
	public void render() {
	    super.render();

	}
}

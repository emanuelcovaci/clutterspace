package com.tmc.clutterspace.core.utility;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;


/**
 * Wrapper singleton class for {@link AssetManager} to be able to be accessed globaly.
 * <p>
 * See {@link #getInstance()} for more information.
 * @author roadd
 *
 */
public class AssetLoader {
	private static AssetLoader instance = null;
	public AssetManager assets;
	
	/**
	 * Internal contructor for singleton.
	 */
	protected AssetLoader() {
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assets = new AssetManager(resolver);
		assets.load("background.jpg", Texture.class);
		assets.load("background01.png", Texture.class);
		assets.load("lion.png", Texture.class);
		assets.load("pidgey.png", Texture.class);
		assets.load("background.mp3", Music.class);
   	}
	
	/**
	 * Returns the singleton instance of the {@link AssetManager}.
	 * @return The singletion instance.
	 */
   	public static AssetLoader getInstance() {
      if(instance == null) {
         instance = new AssetLoader();
      }
      return instance;
   	}
   	
   	/**
	 * Convenience method for accessing assets. equivalent to
	 * {@code AssetLoader.getInstance().assets.get(name, clazz)}.
	 * @return The requested asset.
	 */
   	public static <T> T get(String name, Class<T> clazz){
   		return getInstance().assets.get(name, clazz);
   	}
}

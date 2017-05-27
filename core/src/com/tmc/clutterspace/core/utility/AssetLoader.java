package com.tmc.clutterspace.core.utility;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;


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
	public static BiMap<String, Integer> Dictionary = HashBiMap.create();
	private int lastId = 0;
	
	/**
	 * Internal contructor for singleton.
	 */
	protected AssetLoader() {
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assets = new AssetManager(resolver);
		
		load("background.jpg", Texture.class);
		load("background01.png", Texture.class);
		load("heartbeat.png", Texture.class);
		load("lion.png", Texture.class);
		load("pidgey.png", Texture.class);
		load("background.mp3", Music.class);
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
   	
   	private <T> void load(String name, Class<T> clazz){
   		Dictionary.put(name, lastId);
   		lastId++;
		assets.load(name, clazz);
   	}
}

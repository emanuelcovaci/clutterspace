package com.tmc.clutterspace.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.tmc.clutterspace.core.Client;
import com.tmc.clutterspace.core.Server;

public class ClientDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Client(), config);
	}
}

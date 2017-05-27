package com.tmc.clutterspace.core.engine;

import com.tmc.clutterspace.core.engine.components.Component;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;


public class State{
	/**
	 * The type id of {@link Component} of this {@link State}.
	 */
	public int typeId = -1;
	
	/**
	 * The values array of the {@link State}.
	 */
	public byte[] values = new byte[]{};

	/**
	 * The constructor. It saves the UUID of the linked {@link GameObject} and the type of {@link Component}.
	 * @param component The {@link Component} of which state we create.
	 */
	public State(Component component){
		typeId = Component.Dictionary.get(component.getClass());
	}
	
	/**
	 * Empty contructor.
	 */
	private State(){
		
	}
	
	/**
	 * Get the serializable content of the {@link State}.
	 * @return The serialized content.
	 */
	public byte[] serialize(){
		if(values.length == 0) return new byte[]{};

		ByteBuffer buf = ByteBuffer.allocate(8 + values.length);
		buf.putInt(typeId);
		buf.putInt(values.length);
		buf.put(values);
		return buf.array();
	}
	
	/**
	 * This function deserializes a {@link State} from a byte array.
	 * @param barr The serialized form of the {@link State}.
	 * @return The deserialized {@link State}
	 */
	public static State deserialize(byte [] barr){
		ByteBuffer buf = ByteBuffer.wrap(barr); 
		State s = new State();
		s.typeId = buf.getInt();
		int nr = buf.getInt();
		s.values = new byte[nr];
		buf.get(s.values);
		return s;
	}
}

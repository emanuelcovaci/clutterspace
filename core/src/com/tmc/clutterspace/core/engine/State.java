package com.tmc.clutterspace.core.engine;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import com.tmc.clutterspace.core.engine.components.Component;

public class State{
	/**
	 * The type id of {@link Component} of this {@link State}.
	 */
	public int typeId = -1;
	
	/**
	 * The values array of the {@link State}.
	 */
	public ArrayList<Integer> values = new ArrayList<Integer>();

	/**
	 * The constructor. It saves the UUID of the linked {@link GameObject} and the type of {@link Component}.
	 * @param component The {@link Component} of which state we create.
	 */
	public State(Component component){
//		typeId = component;
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
	public ArrayList<Integer> getSerializableData(){
		if(values.isEmpty()) return new ArrayList<Integer>();
		ArrayList<Integer> barr = new ArrayList<Integer>();
		barr.add(typeId);
		barr.add(values.size());
		barr.addAll(values);
		return barr;
	}
	
	/**
	 * This function deserializes a {@link State} from a byte array.
	 * @param barr The serialized form of the {@link State}.
	 * @return The deserialized {@link State}
	 */
	public static State deserialize(byte [] barr){
		IntBuffer intBuf = ByteBuffer.wrap(barr)
				     				 .order(ByteOrder.BIG_ENDIAN)
				     				 .asIntBuffer();
		int[] a = new int[intBuf.limit()];
		intBuf.get(a);
		State s = new State();
		s.typeId = a[0];
		for(int i = 2; i < a[1] + 2; i++){
			s.values.add(a[i]);
		}
		return s;
	}
}

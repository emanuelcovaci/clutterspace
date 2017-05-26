package com.tmc.clutterspace.core.engine;

import java.io.*;
import java.util.ArrayList;

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
	 * THe constructor. It saves the UUID of the linked {@link GameObject} and the type of {@link Component}.
	 * @param component The {@link Component} of which state we create.
	 */
	public State(Component component){
//		typeId = component;
	}
	
	/**
	 * Serializes this object to a byte array.
	 * @return The serialized form of the {@link State}.
	 * @throws IOException
	 */
	public ArrayList<Integer> getContent(){
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
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static State deserialize(byte [] barr) throws IOException , ClassNotFoundException{
		try(ByteArrayInputStream bai = new ByteArrayInputStream(barr)){
			try(ObjectInputStream in = new ObjectInputStream(bai)){
				return (State)in.readObject();
			}
		}
	}
}

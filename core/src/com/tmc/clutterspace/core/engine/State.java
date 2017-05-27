package com.tmc.clutterspace.core.engine;

import java.io.*;
import java.util.HashMap;

public class State implements  Serializable{
	public HashMap<String, Object> values = new HashMap<String, Object>();

	public byte [] serialize() throws IOException {
		try(ByteArrayOutputStream bao = new ByteArrayOutputStream()){
			try(ObjectOutputStream out = new ObjectOutputStream(bao)){
				out.writeObject(this);
				return bao.toByteArray();
			}
		}
	}
	public static State deserialize(byte [] barr) throws IOException , ClassNotFoundException{
		try(ByteArrayInputStream bai = new ByteArrayInputStream(barr)){
			try(ObjectInputStream in = new ObjectInputStream(bai)){
				return (State)in.readObject();
			}
		}
	}
}

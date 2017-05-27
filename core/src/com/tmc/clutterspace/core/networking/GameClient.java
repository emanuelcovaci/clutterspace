package com.tmc.clutterspace.core.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.State;

/**
 * Created by softmandar on 26.05.2017.
 */
public class GameClient {

    private static final int MAX_BUFF = 1024;
    private DatagramChannel client_channel;
    public static InetSocketAddress server_address;

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        new GameClient();
    }
    GameClient() throws IOException{
        client_channel = DatagramChannel.open();
        client_channel.bind(null);
        server_address = new InetSocketAddress("10.1.0.68",8080);
    }

    public void send_data(GameObject obj) throws IOException{
            ByteBuffer plbuff_out = ByteBuffer.allocate(MAX_BUFF);
            plbuff_out.clear();

    }

    public ArrayList<State> recieve_data()throws IOException{
            ByteBuffer plbuff_in  = ByteBuffer.allocate(MAX_BUFF);
            plbuff_in.clear();
            client_channel.receive(plbuff_in);
            return (ArrayList<State>)State.deserialize(plbuff_in.array());

    }
}

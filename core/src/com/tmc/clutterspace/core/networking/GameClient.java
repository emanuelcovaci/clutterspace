package com.tmc.clutterspace.core.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;

import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.State;

/**
 * Created by softmandar on 26.05.2017.
 */
public class GameClient {

    private static final int MAX_BUFF = 1024000;
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

    public void send_data(byte [] plobj) throws IOException{
            System.out.println(plobj.length);
            ByteBuffer plbuff_out = ByteBuffer.wrap(plobj = new byte[plobj.length]);
            plbuff_out = ByteBuffer.allocate(plobj.length);
            plbuff_out = ByteBuffer.allocateDirect(plobj.length);
            client_channel.send(plbuff_out,server_address);
    }

    public byte [] recieve_data()throws IOException{
            ByteBuffer plbuff_in  = ByteBuffer.allocate(MAX_BUFF);
            plbuff_in.clear();
            client_channel.receive(plbuff_in);
            return plbuff_in.array();

    }
}

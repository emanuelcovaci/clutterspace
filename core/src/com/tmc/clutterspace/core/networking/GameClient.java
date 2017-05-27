package com.tmc.clutterspace.core.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by softmandar on 26.05.2017.
 */
public class GameClient {

    private static final int MAX_BUFF = 1024;
    private DatagramChannel client_channel;
    public static InetSocketAddress server_address;

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        new GameClient().send_info();
    }
    GameClient() throws IOException{
        client_channel = DatagramChannel.open();
        client_channel.bind(null);
        server_address = new InetSocketAddress("10.1.0.68",8080);
    }

    public void send_info() throws ClassNotFoundException, IOException{
        ByteBuffer client_buff = ByteBuffer.allocate(MAX_BUFF);
        client_buff.clear();
        client_buff.put("SoftMandar".getBytes());
        client_buff.clear();
        client_buff.flip();
        client_channel.send(client_buff, server_address);
        System.out.println(client_channel.receive(client_buff).toString());
    }
}

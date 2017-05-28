package com.tmc.clutterspace.core.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by softmandar on 26.05.2017.
 */
public class GameClient {

    private static final int MAX_BUFF = 1000000;
    private DatagramChannel client_channel;
    public static InetSocketAddress server_address;

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        new GameClient();
    }
    GameClient() throws IOException{
        client_channel = DatagramChannel.open();
        client_channel.bind(null);
        server_address = new InetSocketAddress("10.1.0.68",8080);
        String playername = "Rares";
        while(true){
            send_data(playername.getBytes());
            System.out.println("Player just recieved" + recieve_data().length + "bytes");
        }
    }

    public void send_data(byte [] plobj) throws IOException{
            ByteBuffer plbuff_out = ByteBuffer.allocate(MAX_BUFF);
            plbuff_out.clear();
            plbuff_out.put(plbuff_out);
            client_channel.send(plbuff_out,server_address);
    }

    public byte [] recieve_data()throws IOException{
            ByteBuffer plbuff_in  = ByteBuffer.allocate(MAX_BUFF);
            plbuff_in.clear();
            client_channel.receive(plbuff_in);
            return plbuff_in.array();

    }
}

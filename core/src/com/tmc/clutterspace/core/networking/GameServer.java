package com.tmc.clutterspace.core.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;

/**
 * Created by softmandar on 26.05.2017.
 */
public class GameServer {

    HashMap<SocketAddress, ClientHandler> connected_players;
    private DatagramChannel server_channel;
    private static final int MAX_SIZE = 1024;
    public static void main(String[] args) throws IOException{
        new GameServer(new InetSocketAddress("10.1.0.68",8080));
    }
    GameServer(InetSocketAddress listen_address) throws IOException{
        try{
            server_channel = DatagramChannel.open();
            server_channel.bind(listen_address);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Server just started on" + listen_address.getAddress() + ":" + listen_address.getPort());

        connected_players = new HashMap<>();
        new GameRoom(server_channel, connected_players).connect_players();
        start_game();
    }

    public void start_game() throws IOException{
        boolean running = true;
        ByteBuffer player_buff = ByteBuffer.allocate(MAX_SIZE);
        while(running){
            player_buff.clear();
            this.server_channel.receive(player_buff);
            if(connected_players.isEmpty()){
                running = false;
            }else{
                for(SocketAddress player_addr: connected_players.keySet()){
                    player_buff.flip();
                    this.server_channel.send(player_buff,player_addr);
                    player_buff.clear();
                }
            }

        }
    }
    public void get_players(){

    }
}

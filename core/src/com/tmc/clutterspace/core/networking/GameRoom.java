package com.tmc.clutterspace.core.networking;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.*;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.tmc.clutterspace.core.engine.GameObject;

/**
 * Created by softmandar on 26.05.2017.
 */
public class GameRoom {

    private static final int MAX_BYTES = 1024;
    private static final int MAX_PLAYERS = 4;
    private DatagramChannel server_ch;
    private boolean not_ready = true;
    HashMap<SocketAddress, ClientHandler> connpl;
    GameRoom(DatagramChannel srvchannel, HashMap<SocketAddress,ClientHandler> connected_players){
        server_ch = srvchannel;
        connpl = connected_players;
    }

    public boolean canJoin(SocketAddress client_addr, ByteBuffer player_buff){
        if(connpl.size() == MAX_PLAYERS){ return false; }
        return true;
    }
    public boolean allReady(){
        for(Map.Entry<SocketAddress, ClientHandler> player: connpl.entrySet()){
            if(!(player.getValue().is_ready())){
               return false;
            }
        }
        return true;
    }
    public void start_collect() throws IOException{
        not_ready = true;
        ByteBuffer player_buff = ByteBuffer.allocate(MAX_BYTES);
        while(not_ready){
            player_buff.clear();
            SocketAddress cliend_addr = server_ch.receive(player_buff);
            get_player(connpl, cliend_addr).setReady();
        }
    }
    public void start_connect() throws IOException {
        ByteBuffer player_buff = ByteBuffer.allocate(MAX_BYTES);
        while (not_ready) {
            player_buff.clear();
            SocketAddress client_addr = server_ch.receive(player_buff);
            if (canJoin(client_addr, player_buff)) {
                connpl.put(client_addr, new ClientHandler(new String(new byte[player_buff.position()]), client_addr));
                System.out.println("Player just connected with address" + client_addr.toString() + " players connected " + connpl.size());
                for (Map.Entry<SocketAddress, ClientHandler> player : connpl.entrySet()) {
                    System.out.println("Send to " + player.getKey().toString());
                    player_buff.clear();
                    player_buff.put(player.getValue().plusername.getBytes());
                    player_buff.flip();
                    server_ch.send(player_buff, player.getKey());
                }
            }else{
                System.out.println("The server room si already full please wait!!!");
            }
        }

    }
    public static <T,E> E get_player(Map<T, E>map, T key){
        for(Map.Entry<T,E> entry : map.entrySet()){
            if(Objects.equals(key,entry.getKey())){
                return entry.getValue();
            }
        }
        return null;
    }
}


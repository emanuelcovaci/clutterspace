package com.tmc.clutterspace.core.networking;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.*;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by softmandar on 26.05.2017.
 */
public class GameRoom {

    private static final int MAX_BYTES = 1024;
    private static final int MAX_PLAYERS = 6;
    private DatagramChannel server_ch;
    private boolean not_ready = true;
    HashMap<SocketAddress, ClientHandler> connpl;
    GameRoom(DatagramChannel srvchannel, HashMap<SocketAddress,ClientHandler> connected_players){
        server_ch = srvchannel;
        connpl = connected_players;
    }

    public void connect_players()throws IOException{
        ByteBuffer player_buff = ByteBuffer.allocate(MAX_BYTES);
        while(not_ready){
            player_buff.clear();
            SocketAddress client_addr = server_ch.receive(player_buff);
            System.out.println("Player just connected with address" + client_addr.toString());
            connpl.put(client_addr, new ClientHandler(new String(player_buff.array())));
            for(Map.Entry<SocketAddress, ClientHandler> player: connpl.entrySet()){
                System.out.println("Sebd to " + player.getKey().toString());
                player_buff.clear();
                player_buff.put(player.getValue().plusername.getBytes());
                player_buff.flip();
                server_ch.send(player_buff, player.getKey());
            }
            if(connpl.size() == MAX_PLAYERS){
                not_ready = false;
                get_ready();
            }

        }
    }
    public boolean verify_player(SocketAddress playeraddr){
        for(Map.Entry<SocketAddress, ClientHandler> player: connpl.entrySet()){
            if(playeraddr == player.getKey() && player.getValue().is_ready()){
                return true;
            }
        }
        return false;
    }
    public void get_ready() throws IOException{
        String ready_state = "Yes";
        ByteBuffer get_ready  = ByteBuffer.allocate(MAX_BYTES);
        while (not_ready) {
            SocketAddress ready_player = server_ch.receive(get_ready);
            if(verify_player(ready_player)){
                get_ready.clear();
                get_ready.put(ready_state.getBytes());
                get_ready.flip();
                server_ch.send(get_ready, ready_player);
            }

        }
    }

}

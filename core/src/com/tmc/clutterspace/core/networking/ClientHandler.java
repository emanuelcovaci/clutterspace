package com.tmc.clutterspace.core.networking;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

/**
 * Created by softmandar on 26.05.2017.
 */
public class ClientHandler {

    public boolean is_ready = false;

    public String plusername;
    private SocketAddress player_addr;
    ClientHandler(){

    }

    ClientHandler(String username, SocketAddress player_address){
        this.plusername = username;
        this.player_addr =  player_address;

    }
    public boolean is_ready(){
        return is_ready;
    }
    public void  response(byte [] resp_data){
        //ByteBuffer resp_buff = ByteBuffer.allocate();
    }
    public void setReady(){
        if(is_ready){
            this.is_ready = false;
        }
        this.is_ready = true;

    }

}

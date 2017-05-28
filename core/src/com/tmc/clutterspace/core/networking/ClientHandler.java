package com.tmc.clutterspace.core.networking;

/**
 * Created by softmandar on 26.05.2017.
 */
public class ClientHandler {

    public boolean is_ready = false;

    public String plusername;

    ClientHandler(){

    }

    ClientHandler(String username){
        plusername = username;

    }
    public boolean is_ready(){
        return is_ready;
    }
    
}

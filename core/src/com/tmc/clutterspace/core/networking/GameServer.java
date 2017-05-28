package com.tmc.clutterspace.core.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;

import javafx.util.Pair;

/**
 * Created by softmandar on 26.05.2017.
 */
public class GameServer {

    HashMap<SocketAddress, ClientHandler> connected_players;
    public static String SERVER_ADRESS = "10.1.0.68";
    public static int PORT = 8080;
    private DatagramChannel server_channel;
    private static final int MAX_SIZE = 1024;
    private static final int MAX_PLAYERS = 4;
    public static void main(String[] args) throws IOException{
        new GameServer(new InetSocketAddress(SERVER_ADRESS,8080));
    }

    /**
     *
     * @param listen_address
     * @throws IOException
     */
    GameServer(InetSocketAddress listen_address) throws IOException{
        try{
            server_channel = DatagramChannel.open();
            server_channel.bind(listen_address);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Server just started on" + listen_address.getAddress() + ":" + listen_address.getPort());

        connected_players = new HashMap<>();
        //new GameRoom(server_channel, connected_players).start_connect();
        start_game();
    }

    /**
     *
     * @throws IOException
     */
    public boolean canJoin(SocketAddress client_addr, ByteBuffer player_buff){
        if(connected_players.size() == MAX_PLAYERS){ return false; }
        return true;
    }
    public void start_game() throws IOException{
        boolean running = true;
        ByteBuffer player_buff = ByteBuffer.allocate(MAX_SIZE);
        int current_size = player_buff.position();
        System.out.println("Game server just started listen for player data");
        SocketAddress client_addr = server_channel.receive(player_buff);
        if(canJoin(client_addr, player_buff)) {
            System.out.println("Another player joined the server " + connected_players.size() + " players online");
            connected_players.put(client_addr, new ClientHandler(new String(new byte[current_size]), client_addr));
        }else{
            System.out.println("The server is already full");
        }
        Runnable start = () -> {
            player_buff.clear();
            try {
                this.server_channel.receive(player_buff);
                for(SocketAddress player_addr: connected_players.keySet()){
                    int current_size2 = player_buff.position();
                    player_buff.flip();
                    ByteBuffer pl_trans = ByteBuffer.wrap(new byte[current_size2]);
                    pl_trans.put(player_buff.array());
                    this.server_channel.send(pl_trans,player_addr);
                    player_buff.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        };

        Thread t = new Thread(start);
        t.start();

    }
    /**
     *
     */
    public Pair<SocketAddress, byte[]> recieve() throws IOException{
        ByteBuffer plbuff = ByteBuffer.allocate(MAX_SIZE);
        plbuff.clear();
        SocketAddress player_addr = server_channel.receive(plbuff);
        return new Pair<SocketAddress, byte[]>(player_addr,plbuff.array());
    }
    public SocketAddress get_players() throws IOException{
        return (SocketAddress)connected_players.keySet();

    }
}

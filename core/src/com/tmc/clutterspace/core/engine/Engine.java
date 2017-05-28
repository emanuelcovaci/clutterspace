package com.tmc.clutterspace.core.engine;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.tmc.clutterspace.core.engine.components.Component;
import com.tmc.clutterspace.core.factories.PlayerFactory;
import com.tmc.clutterspace.core.networking.Connect;
import com.tmc.clutterspace.core.networking.Packet;

import box2dLight.RayHandler;;

public class Engine {
	private static Engine instance = null;
	
	private HashMap<Long, GameObject> entities;
	private World world;
    private RayHandler rayHandler;
    private Box2DDebugRenderer debugRenderer;
    private SpriteBatch batch;
    private OrthographicCamera cam;
    private LinkedList<byte[]> snapshots = new LinkedList<byte[]>();
    private long startTime;
    private long currentTime;
    public float step = 1/60f;
    public int latency = 100;
    public int snapRate = 5;
    private int gameTick = 0;
    public boolean debug = false;
    private Client client;
    private Server server;
    
    private HashMap<Connection, byte[]> inputs;
    private HashMap<Connection, GameObject> players;
    
	private Engine(){
		world = new World(new Vector2(0, 0), true);
		entities = new HashMap<Long, GameObject>();
		rayHandler = new RayHandler(world);
		batch = new SpriteBatch();
        cam = new OrthographicCamera(1920, 1080);
        cam.setToOrtho(false, 1920, 1080);
        debugRenderer = new Box2DDebugRenderer();
//        Viewport viewport = new StretchViewport(cam.viewportWidth, cam.viewportHeight, cam);
//		viewport.update((int)cam.viewportWidth, (int)cam.viewportHeight, true);
        rayHandler.setCombinedMatrix(cam);
        rayHandler.setShadows(false);
	}
	
	public static Engine getInstance(){
		if(instance == null)
			instance = new Engine();
		return instance;
	}
	
	public void setCamera(OrthographicCamera cam){
		this.cam = cam;
		rayHandler.setCombinedMatrix(cam);
	}
	
	public OrthographicCamera getCamera(){
		return cam;
	}
	
	public SpriteBatch getBatch(){
		return batch;
	}
	
	public HashMap<Long, GameObject> getEntities() {
		return entities;
	}

	public void addEntities(GameObject obj) {
		entities.put(obj.id, obj);
	}	
	
	public World getWorld() {
		return world;
	}

	public RayHandler getRayHandler() {
		return rayHandler;
	}

	public Box2DDebugRenderer getDebugRenderer() {
		return debugRenderer;
	}
	
	
	public void startServer() throws IOException{
		server = new Server();
		server.getKryo().register(Packet.class);
		server.getKryo().register(byte[].class);
		server.getKryo().register(Connect.class);
		server.getKryo().register(String.class);
	    new Thread(server).start();
	    server.bind(54555, 54777);
		entities.values().stream().forEach(GameObject::init);
		players = new HashMap<Connection, GameObject>();
		inputs = new HashMap<Connection, byte[]>();
		server.addListener(new Listener() {
		       public void received (Connection connection, Object object) {
		          if (object instanceof Packet) {
		        	  Packet request = (Packet)object;
		        	  inputs.put(connection, request.data);
		          }
		          else if(object instanceof Connect) {
		        	  synchronized(players){
		        	 	players.put(connection, PlayerFactory.create());
		        	  }
		        	  addEntities(players.get(connection));
		        	  
		        	  connection.sendTCP(ByteBuffer.allocate(8).putLong(getTime()).array());
		          }
		       }
		    });
	}
	
	public void startClient() throws IOException{
		client = new Client();
		client.getKryo().register(Packet.class);
		client.getKryo().register(byte[].class);
		client.getKryo().register(Connect.class);
		client.getKryo().register(String.class);
		new Thread(client).start();
		
		boolean connected = false;
		while(!connected)
		    try{
		    	client.connect(5000, "127.0.0.1", 54555, 54777);
		    	connected = true;
		    } catch (Exception e){
		    	
		    }
		
	    InetAddress addr;
	    addr = InetAddress.getLocalHost();
	    Connect cnt = new Connect();
	    cnt.name = addr.getHostName();
	    client.sendTCP(cnt);
	    
	    new Thread(() -> {
	    	while(true){	
				ByteBuffer buf = ByteBuffer.allocate(8 + 16);
				buf.putInt(Gdx.input.getX());
				buf.putInt(Gdx.input.getY());
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.A) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.S) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.UP) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.LEFT) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.DOWN) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.Q) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.E) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.R) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.SPACE) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.NUM_1) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.NUM_2) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.NUM_3) ? 1 : 0));
				buf.put((byte)(Gdx.input.isKeyPressed(Input.Keys.NUM_4) ? 1 : 0));
				
				Packet p = new Packet();
				p.data = buf.array();
				client.sendUDP(p);
	    	}	
	    }).start();
	    
	    client.addListener(new Listener() {
	       	public void received (Connection connection, Object object) {
		        if (object instanceof Packet) {
		            Packet request = (Packet)object;
		        	snapshots.add(request.data);
		        }
		        if(object instanceof byte[]){
		        	byte[] by = (byte[]) object;
		        	System.out.println(System.currentTimeMillis() - ByteBuffer.wrap(by).getLong());
		        	setStartTime(System.currentTimeMillis() - ByteBuffer.wrap(by).getLong());
		        }
		    }
	    });
	    
	}
	
	
	public byte[] createSnapshot() throws IOException{
		ByteBuffer buf = ByteBuffer.allocate(8);
		byte[] ret = buf.putLong(getTime()).array();
		buf = ByteBuffer.allocate(4);
		for(GameObject obj : entities.values()){
			byte[] ser = obj.serialize();
			System.out.println(obj + " " + ser.length);
			if(ser.length == 0) continue;
			ret = (byte[])ArrayUtils.addAll(ret, buf.putInt(ser.length).array());
			buf.clear();
			ret = (byte[])ArrayUtils.addAll(ret, ser);
		}
		return ret;	
	}
	
	public ArrayList<GameObject> decodeSnapshots(byte[] snap){
		ArrayList<GameObject> objs = new ArrayList<GameObject>();
		ByteBuffer buf = ByteBuffer.wrap(snap);
		long time = buf.getLong();
		while(buf.position() < buf.limit()){
			int nr = buf.getInt();
			objs.add(GameObject.deserialize(Arrays.copyOfRange(snap, buf.position(), buf.position() + nr)));
			buf.position(buf.position() + nr);
		}
		return objs;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public long getTime(){
		return currentTime - getStartTime();
	}
	
	public void setCurrentTime(){
		currentTime = System.currentTimeMillis();;
	}
	
	public void update(){
		setCurrentTime();
		if(gameTick == 0) startTime = currentTime;
		if(server != null){
			if(gameTick % snapRate == 0)
				try {
					byte[] snap = createSnapshot();
					Packet p = new Packet();
					p.data = snap;
					players.keySet().forEach(a -> a.sendUDP(p));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			synchronized (players){
				entities.values().stream().filter(a -> !players.containsValue(a)).forEach(GameObject::prepare);
			}
			for(HashMap.Entry<Connection, GameObject> e : players.entrySet()){
				Inputs.setValues(inputs.get(e.getKey()));
				e.getValue().prepare();
			}
			synchronized (players){
				entities.values().stream().filter(a -> !players.containsValue(a)).forEach(a -> a.update(step));
			}
			for(HashMap.Entry<Connection, GameObject> e : players.entrySet()){
				Inputs.setValues(inputs.get(e.getKey()));
				e.getValue().update(step);
			}
			world.step(step, 6, 2);

			synchronized (players){
				entities.values().stream().filter(a -> !players.containsValue(a)).forEach(GameObject::postUpdate);
			}
			for(HashMap.Entry<Connection, GameObject> e : players.entrySet()){
				Inputs.setValues(inputs.get(e.getKey()));
				e.getValue().postUpdate();
			}
		}
		gameTick += 1;
	}
	
	
	public void render()
	{	
		if(getTime() - latency < 0) return;
		
		byte[] s = null;
		byte[] p = null;
		float perc = 0;
		while(s == null){
			if(snapshots.size() < 2) return; 
			s = snapshots.get(1);
			ByteBuffer buf = ByteBuffer.wrap(s);
			long time = buf.getLong();
			if(getTime() - latency > time){
				snapshots.pop();
				s = null;
			}
			else{
				p = snapshots.get(0);
				long aux = ByteBuffer.wrap(p).getLong();
				long delta = time - aux;
				long timeDif = getTime() - latency - aux;
				perc = timeDif * 1.0f / delta;
			}		
		}
		
		ArrayList<GameObject> entities = interpolate(decodeSnapshots(p), decodeSnapshots(s),perc);
		
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
		entities.stream().forEach(a -> a.preRender(batch));
        batch.begin();
		entities.stream().forEach(a -> a.render(batch));
		entities.stream().forEach(a -> a.onGui(batch));
		batch.end();
        rayHandler.updateAndRender();
        rayHandler.removeAll();
        if(debug)
        	debugRenderer.render(world, cam.combined);
	}

	@SuppressWarnings("unchecked")
	private ArrayList<GameObject> interpolate(ArrayList<GameObject> first, ArrayList<GameObject> sec, float perc){
		HashMap<Long, GameObject> secDict = new HashMap();
		sec.stream().forEach(a -> secDict.put(a.id, a));
		
		ArrayList<GameObject> ret = new ArrayList<GameObject>();
		for(GameObject obj : first){
			GameObject temp = obj.interpolate(secDict.getOrDefault(obj.id, null), perc);	
			System.out.println(temp);
			temp.init();
			ret.add(temp);
		}
		return ret;
	}
	
	public static class Inputs{
		public static int POS_X = 0;
		public static int POS_Y = 0;

		public static boolean W = false;
		public static boolean A = false;
		public static boolean S = false;
		public static boolean D = false;
		public static boolean UP = false;
		public static boolean LEFT = false;
		public static boolean DOWN = false;
		public static boolean RIGHT = false;
		public static boolean Q = false;
		public static boolean E = false;
		public static boolean R = false;
		public static boolean SPACE = false;
		public static boolean NUM_1 = false;
		public static boolean NUM_2 = false;
		public static boolean NUM_3 = false;
		public static boolean NUM_4 = false;
		
		public static void setValues(byte[] data){
			if(data == null || data == new byte[]{}) 
				data = new byte[24];
			ByteBuffer buf = ByteBuffer.wrap(data);
			POS_X = buf.getInt();
			POS_Y = buf.getInt();
			W = buf.get() == 1;
			A = buf.get() == 1;
			S = buf.get() == 1;
			D = buf.get() == 1;
			UP = buf.get() == 1;
			LEFT = buf.get() == 1;
			DOWN = buf.get() == 1;
			RIGHT = buf.get() == 1;
			Q = buf.get() == 1;
			E = buf.get() == 1;
			R = buf.get() == 1;
			SPACE = buf.get() == 1;
			NUM_1 = buf.get() == 1;
			NUM_2 = buf.get() == 1;
			NUM_3 = buf.get() == 1;
			NUM_4 = buf.get() == 1;
		}
		
	}
	
	
}

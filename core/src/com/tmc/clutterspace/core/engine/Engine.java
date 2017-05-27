package com.tmc.clutterspace.core.engine;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang3.ArrayUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tmc.clutterspace.core.engine.components.Component;

import box2dLight.RayHandler;;

public class Engine {
	private static Engine instance = null;
	
	private HashMap<Integer, GameObject> entities;
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
    private int gameTick = 0;
    public boolean debug = false;
    

	private Engine(){
		world = new World(new Vector2(0, 0), true);
		entities = new HashMap<Integer, GameObject>();
		rayHandler = new RayHandler(world);
		batch = new SpriteBatch();
        cam = new OrthographicCamera(1920, 1080);
        debugRenderer = new Box2DDebugRenderer();
        Viewport viewport = new StretchViewport(cam.viewportWidth, cam.viewportHeight, cam);
		viewport.update((int)cam.viewportWidth, (int)cam.viewportHeight, true);
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
	
	public HashMap<Integer, GameObject> getEntities() {
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
	
	public void startServer(){
		entities.values().stream().forEach(GameObject::init);
	}
	
	private void clientUpdate(){
		
	}
	
	public byte[] createSnapshot() throws IOException{
		ByteBuffer buf = ByteBuffer.allocate(8);
		byte[] ret = buf.putLong(getTime()).array();
		buf = ByteBuffer.allocate(4);
		for(GameObject obj : entities.values()){
			byte[] ser = obj.serialize();
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
		if(gameTick % 25 == 0)
			try {
				snapshots.add(createSnapshot());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		entities.values().stream().forEach(GameObject::prepare);
		entities.values().stream().forEach(a -> a.update(step));
		world.step(step, 6, 2);
		entities.values().stream().forEach(GameObject::postUpdate);
		gameTick += 1;
	}
	
	
	public void render()
	{	
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
		entities.values().stream().forEach(a -> a.render(batch));
		entities.values().stream().forEach(a -> a.onGui(batch));
		batch.end();
        rayHandler.updateAndRender();
        if(debug)
        	debugRenderer.render(world, cam.combined);
	}

}

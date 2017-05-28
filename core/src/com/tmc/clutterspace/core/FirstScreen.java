package com.tmc.clutterspace.core;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tmc.clutterspace.core.collision.BulletPlayerContact;
import com.tmc.clutterspace.core.collision.GroundContact;
import com.tmc.clutterspace.core.engine.Engine;
import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.components.Body2D;
import com.tmc.clutterspace.core.engine.components.Component;
import com.tmc.clutterspace.core.engine.components.GroundSensor;
import com.tmc.clutterspace.core.engine.components.Player;
import com.tmc.clutterspace.core.engine.components.Projectile;
import com.tmc.clutterspace.core.engine.components.Health;
import com.tmc.clutterspace.core.engine.components.Light;
import com.tmc.clutterspace.core.engine.components.Sprite2D;
import com.tmc.clutterspace.core.engine.components.TileMap;
import com.tmc.clutterspace.core.engine.components.TileMapBackground;
import com.tmc.clutterspace.core.engine.components.TileMapShapes;
import com.tmc.clutterspace.core.engine.components.Transform2D;
import com.tmc.clutterspace.core.factories.CornFactory;
import com.tmc.clutterspace.core.factories.DemoLevelFactory;
import com.tmc.clutterspace.core.factories.PlayerFactory;
import com.tmc.clutterspace.core.utility.AssetLoader;

/**
 * Created by emanuel on 20.05.2017.
 */
public class FirstScreen implements Screen {
    final Game game;
    Engine en;
    boolean server = false;

    private Music music_level1;


    public  FirstScreen(Game game, boolean server){

        this.game = game;
        this.server = server;
        

        new Transform2D(0,0);
        new Body2D(BodyType.DynamicBody);
        new GroundSensor();
        new Health();
        new Light(500, true);
        new Player();
        new Projectile(0, new Vector2(0,0));
        new Sprite2D("background.jpg");
        new TileMap("levels/demo.tmx");
        new TileMapBackground(new int[]{});
        new TileMapShapes(new int[]{});
        
        en = Engine.getInstance();
        en.debug = true;
        en.step = 2/60f;
        
        en.getWorld().setGravity(new Vector2(0, -10));
        if(server){
        	try {
				en.startServer();
				DemoLevelFactory.create();
//		        en.addEntities(background);
		        //        GameObject rock = new GameObject();
//		        rock.setComponent(new Transform2D(125,295));
//		        rock.setComponent(new Body2D( BodyType.StaticBody));
		//
//		        rock.init();
		//
//		        PolygonShape rockBox = new PolygonShape();
//		        rockBox.setAsBox(30, 5.0f);
//		        fixture = rock.getComponent(Body2D.class).getBody().createFixture(rockBox, 0.0f);
//		        fixture.setUserData(rock);
		//
//		        GameObject rock2 = new GameObject();
//		        rock2.setComponent(new Transform2D(470,495));
//		        rock2.setComponent(new Body2D( BodyType.StaticBody));
		//
//		        rock2.init();
		//
//		        PolygonShape rockBox2 = new PolygonShape();
//		        rockBox2.setAsBox(30, 5.0f);
//		        fixture = rock2.getComponent(Body2D.class).getBody().createFixture(rockBox2, 0.0f);
//		        fixture.setUserData(rock2);
		//
		//
//		        GameObject rock3 = new GameObject();
//		        rock3.setComponent(new Transform2D(600,280));
//		        rock3.setComponent(new Body2D(BodyType.StaticBody));
		//
//		        rock3.init();
		//
//		        PolygonShape rockBox3 = new PolygonShape();
//		        rockBox3.setAsBox(30, 5.0f);
//		        fixture = rock3.getComponent(Body2D.class).getBody().createFixture(rockBox3, 0.0f);
//		        fixture.setUserData(rock3);        
		       
		        
		        GameObject floor = new GameObject();
		        floor.setComponent(new Transform2D(0, 90));
		        floor.setComponent(new Body2D(BodyType.StaticBody));

		        floor.init();
		        en.addEntities(floor);

		        PolygonShape groundBox = new PolygonShape();
		        groundBox.setAsBox(en.getCamera().viewportWidth, 10.0f);
		        Fixture fixture = floor.getComponent(Body2D.class).getBody().createFixture(groundBox, 0.0f);
		        fixture.setUserData(floor);

		        GameObject player = PlayerFactory.create();
		        en.addEntities(player);
		        
		        GameObject light = new GameObject();
		        light.setComponent(new Transform2D(300, 531));
		        light.setComponent(new Light(200f));
		        light.getComponent(Light.class).getPointLight().setColor(255, 0, 0, 140);

		        light.init();
		        en.addEntities(light);

		        GameObject light2 = new GameObject();
		        light2.setComponent(new Transform2D(1128, 891));
		        light2.setComponent(new Light(300f));
		        light2.getComponent(Light.class).getPointLight().setColor(255, 0, 0, 140);

		        light2.init();
		        en.addEntities(light2);

		        GameObject light3 = new GameObject();
		        light3.setComponent(new Transform2D(1440, 504));
		        light3.setComponent(new Light(200f));
		        light3.getComponent(Light.class).getPointLight().setColor(255, 0, 0, 140);
		        

		        light3.init();
		        en.addEntities(light3);
		        

		        GameObject light4 = new GameObject();
		        light4.setComponent(new Transform2D(1740, 904));
		        light4.setComponent(new Light(200f));
		        light4.getComponent(Light.class).getPointLight().setColor(255, 0, 0, 140);
		        
		        light4.init();
		        en.addEntities(light4);
		        
		        for(int i = 0; i < 5; i++)
		        	en.addEntities(CornFactory.create(new Vector2(500, 500), new Vector2(0,100)));

				
			    Engine.getInstance().getWorld().setContactListener(new GroundContact());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else{
        	try {
				en.startClient();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
//        GameObject background = new GameObject();
//        background.setComponent(new Transform2D(0, 0));
//        background.setComponent(new Body2D(BodyType.StaticBody));
//        background.setComponent(new Sprite2D("background01.png"));
//        background.getComponent(Sprite2D.class).size = new Vector2(en.getCamera().viewportWidth, en.getCamera().viewportHeight);
//
//
//
//
//        background.getComponent(Sprite2D.class).offset = new Vector2(0, 0);
//
//        background.init();\
//        DemoLevelFactory.create();
//        en.addEntities(background);
        //        GameObject rock = new GameObject();
//        rock.setComponent(new Transform2D(125,295));
//        rock.setComponent(new Body2D( BodyType.StaticBody));
//
//        rock.init();
//
//        PolygonShape rockBox = new PolygonShape();
//        rockBox.setAsBox(30, 5.0f);
//        fixture = rock.getComponent(Body2D.class).getBody().createFixture(rockBox, 0.0f);
//        fixture.setUserData(rock);
//
//        GameObject rock2 = new GameObject();
//        rock2.setComponent(new Transform2D(470,495));
//        rock2.setComponent(new Body2D( BodyType.StaticBody));
//
//        rock2.init();
//
//        PolygonShape rockBox2 = new PolygonShape();
//        rockBox2.setAsBox(30, 5.0f);
//        fixture = rock2.getComponent(Body2D.class).getBody().createFixture(rockBox2, 0.0f);
//        fixture.setUserData(rock2);
//
//
//        GameObject rock3 = new GameObject();
//        rock3.setComponent(new Transform2D(600,280));
//        rock3.setComponent(new Body2D(BodyType.StaticBody));
//
//        rock3.init();
//
//        PolygonShape rockBox3 = new PolygonShape();
//        rockBox3.setAsBox(30, 5.0f);
//        fixture = rock3.getComponent(Body2D.class).getBody().createFixture(rockBox3, 0.0f);
//        fixture.setUserData(rock3);        
       
        
//        GameObject floor = new GameObject();
//        floor.setComponent(new Transform2D(0, 90));
//        floor.setComponent(new Body2D(BodyType.StaticBody));
//
//        floor.init();
//        en.addEntities(floor);
//
//        PolygonShape groundBox = new PolygonShape();
//        groundBox.setAsBox(en.getCamera().viewportWidth, 10.0f);
//        Fixture fixture = floor.getComponent(Body2D.class).getBody().createFixture(groundBox, 0.0f);
//        fixture.setUserData(floor);
//
//        GameObject player = PlayerFactory.create();
//        en.addEntities(player);
//        
//        GameObject light = new GameObject();
//        light.setComponent(new Transform2D(379, 640));
//        light.setComponent(new Light(200f));
//        light.getComponent(Light.class).getPointLight().setColor(255, 0, 0, 140);
//
//        light.init();
//        en.addEntities(light);
//
//        GameObject light2 = new GameObject();
//        light2.setComponent(new Transform2D(1128, 861));
//        light2.setComponent(new Light(300f));
//        light2.getComponent(Light.class).getPointLight().setColor(255, 0, 0, 140);
//
//        light2.init();
//        en.addEntities(light2);
//
//        GameObject light3 = new GameObject();
//        light3.setComponent(new Transform2D(1540, 504));
//        light3.setComponent(new Light(200f));
//        light3.getComponent(Light.class).getPointLight().setColor(255, 0, 0, 140);
//
//
//        light3.init();
//        en.addEntities(light3);


//
//        light4.init();
//        en.addEntities(light4);
//        
//        for(int i = 0; i < 5; i++)
//        	en.addEntities(CornFactory.create(new Vector2(500, 500), new Vector2(0,100)));
//
//        music_level1 = AssetLoader.get("background.mp3", Music.class);
//
//		System.out.println(Component.Dictionary);
//        try {
//			System.out.println(en.decodeSnapshots(en.createSnapshot()));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    Engine.getInstance().getWorld().setContactListener(new GroundContact());
//>>>>>>> 47afc51d388381baa43b8f3372fafb45aae525b3
//		Engine.getInstance().getWorld().setContactListener(new BulletPlayerContact());
        System.out.println(Component.Dictionary);
    }
    
	@Override
    public void render(float v) {
    	while(v > 0){
//    		if(server)
			en.update();
    		v -= en.step / 5;
    	}
    	if(!server)
    		en.render();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

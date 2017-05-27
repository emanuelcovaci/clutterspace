package com.tmc.clutterspace.core.engine.components;

import java.nio.ByteBuffer;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.tmc.clutterspace.core.engine.GameObject;
import com.tmc.clutterspace.core.engine.State;

/**
 * Created by emanuel on 27.05.2017.
 */
public class Health extends Component {

    static {
        register(Health.class);
    }
    private Integer health;
    BitmapFont font ;
    GameObject iconHearth;

    public Health(){
        health = 100;
        this.getDependencies().add(Transform2D.class);

    }

    public int getHealth(){
        return health;
    }

    public void setHealth(Integer x){
        health =  x;
    }

    @Override
    protected void initImpl() {

    }

    @Override
    protected void prepareImpl() {

    }

    @Override
    protected void updateImpl(float delta) {

    }

    @Override
    protected void postUpdateImpl() {

    }

    @Override
    protected void renderImpl(SpriteBatch batch) {

        font = new BitmapFont();

        String x = Integer.toString(getHealth());

        font.draw(batch,x, 700, 580);

    }

    @Override
    protected void onGuiImpl(SpriteBatch batch) {

    }

    @Override
	public State getState() {
		ByteBuffer buf = ByteBuffer.allocate(12);
		State s = new State(this);
		buf.putInt(health);
		s.values = buf.array();
		
		return s;
	}
	
	public static Component fromState(State s){
		ByteBuffer buf = ByteBuffer.wrap(s.values);
		Health comp = new Health();
		comp.health = buf.getInt();
		return comp;
	}

	@Override
	public Component interpolateImpl(Component other, float perc) {
		Health comp = new Health();
		Health oth = (Health) other;
		comp.health = (int) (this.health * (1 - perc) + oth.health * perc); 
		return comp;
	}
}

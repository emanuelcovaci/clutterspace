package com.tmc.clutterspace.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by emanuel on 20.05.2017.
 */
public class Bird extends Actor {
    private TextureRegion texture;

    public Bird () {
        texture = new TextureRegion(new Texture(Gdx.files.internal("pidgey.png")));
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
       batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}

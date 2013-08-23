package com.draquod.dungeonEscape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Player extends Entity{

	public float x;
	public float y;
	public float speed = 230.0f;
	private ShapeRenderer sr;
	
	public Player(){
		sr = new ShapeRenderer();
	}
	
	@Override
	public void act(float dt){
		if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
			x -= dt*speed;
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
			x += dt*speed;
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
			y += dt*speed;
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
			y -= dt*speed;
		}
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.setTransformMatrix(batch.getTransformMatrix());
		sr.begin(ShapeType.FilledCircle);
		sr.setColor(0.1f, 0.1f, 0.1f, 1);
		sr.filledCircle(x, y, 10);
		sr.end();
	}
}

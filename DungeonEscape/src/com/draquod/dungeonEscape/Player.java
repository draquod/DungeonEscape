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
	public DungeonGenerator dg;
	
	public Player(){
		sr = new ShapeRenderer();
	}
	
	@Override
	public void act(float dt){
		float newX = x;
		float newY = y;
		if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
			newX -= dt*speed;
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
			newX += dt*speed;
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
			newY += dt*speed;
		}
		if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
			newY -= dt*speed;
		}
		
		if(dg.cells[(int) newX/dg.cell_size/10][(int) newY/dg.cell_size/10] == 0 || dg.cells[(int) newX/dg.cell_size/10][(int) newY/dg.cell_size/10] == dg.PERIMETER){
			
		}else{
			x = newX;
			y = newY;
		}
		
		
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.setTransformMatrix(batch.getTransformMatrix());
		sr.begin(ShapeType.FilledCircle);
		sr.setColor(0.1f, 0.1f, 0.1f, 1);
		sr.filledCircle(x, y, 5);
		sr.end();
	}
}

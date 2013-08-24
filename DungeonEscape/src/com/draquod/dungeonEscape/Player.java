package com.draquod.dungeonEscape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Player extends Entity{

	public float x;
	public float y;
	public float speed = 230.0f;
	private ShapeRenderer sr;
	public DungeonGenerator dg;
	public boolean moving;
	public Vector2 dir;
	
	public Player(){
		sr = new ShapeRenderer();
		dir = new Vector2();

		this.setWidth(20);
		this.setHeight(20);
		this.setBounds(0, 0, getWidth(), getHeight());
	}
	
	@Override
	public void act(float dt){
		float newX = x;
		float newY = y;
		this.setBounds(x-10, y-10, getWidth(), getHeight());
		if(!moving){
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
		}else{
			
			dir.x = Gdx.input.getX() - Gdx.graphics.getWidth()/2+150;
			dir.y = -Gdx.input.getY() + Gdx.graphics.getHeight()/2+70;
			dir = dir.nor();
			
			newX = x + dir.x*speed*dt;
			newY = y + dir.y*speed*dt;
		}
		
		if(dg.cells[(int) newX/dg.cell_size/10][(int) y/dg.cell_size/10] == 0 || dg.cells[(int) newX/dg.cell_size/10][(int) y/dg.cell_size/10] == dg.PERIMETER){
			
		}else{
			x = newX;
		}
		
		if(dg.cells[(int) x/dg.cell_size/10][(int) newY/dg.cell_size/10] == 0 || dg.cells[(int) x/dg.cell_size/10][(int) newY/dg.cell_size/10] == dg.PERIMETER){
			
		}else{
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
		//sr.filledCircle(x-180, y-100, 5);
		sr.end();
	}
}

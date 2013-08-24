package com.draquod.dungeonEscape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Joystick extends Entity{
	
	
	
	private ShapeRenderer sr;
	
	
	public Player player;

	public Joystick(){
		sr = new ShapeRenderer();
		
		
		this.addListener(new InputListener() {
	        public boolean touchDown (InputEvent event, float x1, float y1, int pointer, int button) {
	        		System.out.println(x1*player.dg.cell_size*10 + " " + player.x);
	        		System.out.println(y1*player.dg.cell_size*10 + " " + player.y);
	                System.out.println("down");
	                System.out.println("-------------");
	                player.moving = true;
	                player.dir.x = x1*player.dg.cell_size*10 - player.x;
	                player.dir.y = y1*player.dg.cell_size*10 - player.y;
	                player.dir = player.dir.nor();
	                return true;
	        }
	        
	        public void touchUp (InputEvent event, float x1, float y1, int pointer, int button) {
	                System.out.println("up");
	                player.moving = false;
	        }
		});
		
		this.setWidth(100);
		this.setHeight(100);
		this.setBounds(0, 0, getWidth(), getHeight());
	}
	
	@Override
	public void act(float dt){
		this.setBounds(player.x-150-50, player.y-70-50, getWidth(), getHeight());
	}

	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.setTransformMatrix(batch.getTransformMatrix());
		
		sr.begin(ShapeType.Circle);
		sr.setColor(0.6f, 0.6f, 0.8f, 0.2f);
		//sr.circle(x, y, 25);
		sr.circle(player.x-150, player.y-70, 30);
		sr.end();
	}

}

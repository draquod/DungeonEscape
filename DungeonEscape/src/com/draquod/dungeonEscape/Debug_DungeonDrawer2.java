package com.draquod.dungeonEscape;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Debug_DungeonDrawer2 extends Actor{

	public World w;
	private ShapeRenderer sr;
	float scale = 20;
	public Debug_DungeonDrawer2(){
		sr = new ShapeRenderer();
	}
	public void draw (SpriteBatch batch, float parentAlpha) {
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.setTransformMatrix(batch.getTransformMatrix());
		
		sr.begin(ShapeType.Filled);
    	
    	for(int i =0;i<5;i++){
    		for(int j= 0;j<5;j++){
    			if(w.rooms[i][j] != null){
    				sr.setColor(0.5f, 0.5f, 0.5f, 1);
    				if(w.rooms[i][j].entrance == true){
    					sr.setColor(0.5f, 0.8f, 0.5f, 1);
    				}
    				if(w.rooms[i][j].exit == true){
    					sr.setColor(0.8f, 0.5f, 0.5f, 1);
    				}
    				sr.rect(i*scale, j*scale, scale,  scale);
    			}
    		}
    	}
		
		
			
			
    			
    	
    	
    	sr.end();
	}
	 
}

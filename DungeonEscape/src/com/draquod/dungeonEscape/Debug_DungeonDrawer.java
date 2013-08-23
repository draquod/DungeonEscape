package com.draquod.dungeonEscape;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Debug_DungeonDrawer extends Actor{

	public DungeonGenerator dg;
	private ShapeRenderer sr;
	float scale = 10;
	public Debug_DungeonDrawer(){
		sr = new ShapeRenderer();
	}
	public void draw (SpriteBatch batch, float parentAlpha) {
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.setTransformMatrix(batch.getTransformMatrix());
		
		sr.begin(ShapeType.FilledRectangle);
    	
    	for(int i=0;i<dg.n_cols;i++){
    		for(int j=0;j<dg.n_rows;j++){

    			
    			if(dg.cells[i][j] == dg.CORRIDOR){
    				sr.setColor(0.5f, 0.5f, 0.5f, 1);
    				sr.filledRect(i*dg.cell_size*scale, j*dg.cell_size*scale, dg.cell_size*scale,  dg.cell_size*scale);
    			}else if((dg.cells[i][j] )== dg.ENTRANCE){
    				sr.setColor(0.5f, 0.5f, 0.5f, 1);
    				sr.filledRect(i*dg.cell_size*scale, j*dg.cell_size*scale, dg.cell_size*scale,  dg.cell_size*scale);
    			}else if((dg.cells[i][j] & (~dg.ROOM_ID) )== dg.ROOM){
    				sr.setColor(0.5f, 0.5f, 0.5f, 1);
    				sr.filledRect(i*dg.cell_size*scale, j*dg.cell_size*scale, dg.cell_size*scale,  dg.cell_size*scale);
    			}else if(dg.cells[i][j] == 99){
    				sr.setColor(0.5f, 0.5f, 0.5f, 1);
    				sr.filledRect(i*dg.cell_size*scale, j*dg.cell_size*scale, dg.cell_size*scale,  dg.cell_size*scale);
    			}else if(dg.cells[i][j] == dg.STAIR_DN){
    				sr.setColor(0.3f, 0.8f, 0.3f, 1);
    				sr.filledRect(i*dg.cell_size*scale, j*dg.cell_size*scale, dg.cell_size*scale,  dg.cell_size*scale);
    			}else if(dg.cells[i][j] == dg.STAIR_UP){
    				sr.setColor(0.8f, 0.3f, 0.3f, 1);
    				sr.filledRect(i*dg.cell_size*scale, j*dg.cell_size*scale, dg.cell_size*scale,  dg.cell_size*scale);
    			}
    			
    			
    			/*
    			if(dg.cells[i][j] == dg.CORRIDOR){
    				sr.setColor(0.5f, 0.5f, 0.8f, 1);
    				sr.filledRect(i*dg.cell_size*scale, j*dg.cell_size*scale, dg.cell_size*scale,  dg.cell_size*scale);
    			}else if((dg.cells[i][j] )== dg.ENTRANCE){
    				sr.setColor(0.8f, 0.8f, 0.5f, 1);
    				sr.filledRect(i*dg.cell_size*scale, j*dg.cell_size*scale, dg.cell_size*scale,  dg.cell_size*scale);
    			}else if((dg.cells[i][j] & (~dg.ROOM_ID) )== dg.ROOM){
    				sr.setColor(0.5f, 0.8f, 0.5f, 1);
    				sr.filledRect(i*dg.cell_size*scale, j*dg.cell_size*scale, dg.cell_size*scale,  dg.cell_size*scale);
    			}else if((dg.cells[i][j] )== dg.PERIMETER){
    				sr.setColor(0.8f, 0.5f, 0.5f, 1);
    				sr.filledRect(i*dg.cell_size*scale, j*dg.cell_size*scale, dg.cell_size*scale,  dg.cell_size*scale);
    			}else if(dg.cells[i][j] == 99){
    				sr.setColor(0.8f, 0.5f, 0.8f, 1);
    				sr.filledRect(i*dg.cell_size*scale, j*dg.cell_size*scale, dg.cell_size*scale,  dg.cell_size*scale);
    			}else if(dg.cells[i][j] == dg.STAIR_DN){
    				sr.setColor(0.3f, 0.8f, 0.3f, 1);
    				sr.filledRect(i*dg.cell_size*scale, j*dg.cell_size*scale, dg.cell_size*scale,  dg.cell_size*scale);
    			}else if(dg.cells[i][j] == dg.STAIR_UP){
    				sr.setColor(0.8f, 0.3f, 0.3f, 1);
    				sr.filledRect(i*dg.cell_size*scale, j*dg.cell_size*scale, dg.cell_size*scale,  dg.cell_size*scale);
    			}
    			*/
    			
    		}
    	}
    	
    	
    	sr.end();
    	sr.setColor(0.1f,0.1f, 0.1f, 1);
    	sr.begin(ShapeType.Line);
    	sr.line(0, 0, dg.cell_size*scale*dg.n_cols,0);
    	sr.line(0, dg.cell_size*scale*dg.n_rows, dg.cell_size*scale*dg.n_rows, dg.cell_size*scale*dg.n_cols);
    	sr.line(0, 0, 0, dg.cell_size*scale*dg.n_rows);
    	sr.line(dg.cell_size*scale*dg.n_cols, 0, dg.cell_size*scale*dg.n_cols, dg.cell_size*scale*dg.n_rows);
    	sr.end();
	  
	}
	 
}

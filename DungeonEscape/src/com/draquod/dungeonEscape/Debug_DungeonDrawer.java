package com.draquod.dungeonEscape;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Debug_DungeonDrawer extends Actor{

	public DungeonGenerator dg;
	private ShapeRenderer sr;
	public Debug_DungeonDrawer(){
		sr = new ShapeRenderer();
	}
	public void draw (SpriteBatch batch, float parentAlpha) {

		
		sr.begin(ShapeType.FilledRectangle);
    	
    	for(int i=0;i<dg.n_cols;i++){
    		for(int j=0;j<dg.n_rows;j++){
    			if(dg.cells[i][j] == dg.ROOM){
    				sr.setColor(0.5f, 0.8f, 0.5f, 1);
    				sr.filledRect(i*dg.cell_size, j*dg.cell_size, dg.cell_size,  dg.cell_size);
    			}else if(dg.cells[i][j] == dg.PERIMETER){
    				sr.setColor(0.8f, 0.5f, 0.5f, 1);
    				sr.filledRect(i*dg.cell_size, j*dg.cell_size, dg.cell_size,  dg.cell_size);
    			}
    		}
    	}
    	
    	
    	sr.end();
    	sr.setColor(0.1f,0.1f, 0.1f, 1);
    	sr.begin(ShapeType.Line);
    	sr.line(0, 0, dg.cell_size*dg.n_cols,0);
    	sr.line(0, dg.cell_size*dg.n_rows, dg.cell_size*dg.n_rows, dg.cell_size*dg.n_cols);
    	sr.line(0, 0, 0, dg.cell_size*dg.n_rows);
    	sr.line(dg.cell_size*dg.n_cols, 0, dg.cell_size*dg.n_cols, dg.cell_size*dg.n_rows);
    	sr.end();
	  
	}
	 
}

package com.draquod.dungeonEscape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.graphics.g3d.materials.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;

public class DungeonDrawer {

	public DungeonGenerator dg;
	public Player player;
	float scale = 1;
	public ModelBatch modelBatch;
	public Lights lights;
	public PerspectiveCamera camera;
	public CameraInputController camController;
	public boolean loading;
	public AssetManager assets;
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	Model monkey;
	ModelInstance monkeyInstance;
	public DungeonDrawer() {
		assets = new AssetManager();
		lights = new Lights();
		modelBatch = new ModelBatch();
		//Gdx.gl.glEnable(GL10.GL_FOG);
		//Gdx.gl10.glFogf(GL10.GL_FOG_DENSITY, .01f);
		lights.ambientLight.set(0.6f, 0.6f, 0.6f, 1f);
		//lights.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f,
		//		-0.2f));
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.position.set(50f, 80f, -50f);
		camera.up.set(0, 0, -1);
		camera.lookAt(5*10, 0, -5*10);
		camera.near = 0.01f;
		camera.far = 300f;
		camera.update();
		camController = new CameraInputController(camera);
        //Gdx.input.setInputProcessor(camController);
        camController.target.set(5*10, 0, 5*10);
        
        
        assets.load("data/normalTile.obj", Model.class);
        assets.load("data/corridorTile.obj", Model.class);
        assets.load("data/deadEndTile.obj", Model.class);
        assets.load("data/turnTile.obj", Model.class);
        assets.load("data/wallTile.obj", Model.class);
        assets.load("data/monkey.obj",Model.class);
        loading = true;
	}
	
	private void doneLoading() {
		monkey = assets.get("data/monkey.obj", Model.class);
		
        Model normalTile = assets.get("data/normalTile.obj", Model.class);
        Model corridorTile = assets.get("data/corridorTile.obj", Model.class);
        Model deadEndTile = assets.get("data/deadEndTile.obj", Model.class);
        Model turnTile = assets.get("data/turnTile.obj", Model.class);
        Model wallTile = assets.get("data/wallTile.obj", Model.class);
        monkeyInstance = new ModelInstance(monkey);
        monkeyInstance.materials.get(0).set(ColorAttribute.createDiffuse(0.1f, 0.1f, 1.0f, 1.0f));
        ModelInstance instance;
        for(int i=1;i<dg.n_cols-1;i++){
        	for(int j =1;j<dg.n_rows-1;j++){
        		
        		if(dg.cells[i][j] != 0 && dg.cells[i][j] != dg.PERIMETER){
        			
        			
        			if(dg.cells[i+1][j] != 0 &&
        					(dg.cells[i-1][j] == dg.PERIMETER || dg.cells[i-1][j] == 0) &&
        					dg.cells[i][j+1] != 0 &&
        					(dg.cells[i][j-1] == dg.PERIMETER || dg.cells[i][j-1] == 0) ){
        				instance = new ModelInstance(turnTile); 
        				instance.transform.translate(i*dg.cell_size*scale,0, -j*dg.cell_size*scale);
        				instance.transform.rotate(0, 1, 0, 90);
        				instances.add(instance);
        			}else
        			if((dg.cells[i+1][j] == dg.PERIMETER || dg.cells[i+1][j] == 0) &&
	    					dg.cells[i-1][j] != 0 &&
	    					dg.cells[i][j+1] != 0 &&
	    					(dg.cells[i][j-1] == dg.PERIMETER || dg.cells[i][j-1] == 0) ){
	    				instance = new ModelInstance(turnTile); 
	    				instance.transform.translate(i*dg.cell_size*scale,0, -j*dg.cell_size*scale);
	    				instances.add(instance);
	    			}else
	    			if((dg.cells[i+1][j] == dg.PERIMETER || dg.cells[i+1][j] == 0) &&
	    					dg.cells[i-1][j] != 0 &&
	    					(dg.cells[i][j+1] == dg.PERIMETER || dg.cells[i][j+1] == 0) &&
	    					dg.cells[i][j-1] != 0 ){
	    				instance = new ModelInstance(turnTile); 
	    				instance.transform.translate(i*dg.cell_size*scale,0, -j*dg.cell_size*scale);
	    				instance.transform.rotate(0, 1, 0, -90);
	    				instances.add(instance);
	    			}else
	    			if(dg.cells[i+1][j] != 0 &&
	    					(dg.cells[i-1][j] == dg.PERIMETER || dg.cells[i-1][j] == 0) &&
	    					(dg.cells[i][j+1] == dg.PERIMETER || dg.cells[i][j+1] == 0)&&
	    					dg.cells[i][j-1] != 0 ){
	    				instance = new ModelInstance(turnTile); 
	    				instance.transform.translate(i*dg.cell_size*scale,0, -j*dg.cell_size*scale);
	    				instance.transform.rotate(0, 1, 0, 180);
	    				instances.add(instance);
	    			}else
        			if(dg.cells[i+1][j] != 0 && dg.cells[i+1][j] != dg.PERIMETER &&
        					dg.cells[i-1][j] != 0 && dg.cells[i-1][j] != dg.PERIMETER &&
        					dg.cells[i][j+1] !=0 && dg.cells[i][j+1] != dg.PERIMETER &&
        					(dg.cells[i][j-1] == dg.PERIMETER || dg.cells[i][j-1] == 0)  ){
        				instance = new ModelInstance(wallTile); 
        				instance.transform.translate(i*dg.cell_size*scale,0, -j*dg.cell_size*scale);
        				instances.add(instance);
        			}else
        			if(dg.cells[i+1][j] != 0 && dg.cells[i+1][j] != dg.PERIMETER &&
	    					dg.cells[i-1][j] != 0 && dg.cells[i-1][j] != dg.PERIMETER &&
	    					(dg.cells[i][j+1] == dg.PERIMETER || dg.cells[i][j+1] == 0) &&
	    					dg.cells[i][j-1] != 0  && dg.cells[i][j-1] != dg.PERIMETER ){
	    				instance = new ModelInstance(wallTile); 
	    				instance.transform.translate(i*dg.cell_size*scale,0, -j*dg.cell_size*scale);
	    				instance.transform.rotate(0, 1, 0, 180);
	    				instances.add(instance);
        			}else
        			if((dg.cells[i+1][j] == dg.PERIMETER || dg.cells[i+1][j] == 0) &&
	    					dg.cells[i-1][j] != 0 && dg.cells[i-1][j] != dg.PERIMETER &&
	    					dg.cells[i][j+1] != 0 && dg.cells[i][j+1] != dg.PERIMETER &&
	    					dg.cells[i][j-1] != 0 && dg.cells[i][j-1] != dg.PERIMETER ){
	    				instance = new ModelInstance(wallTile); 
	    				instance.transform.translate(i*dg.cell_size*scale,0, -j*dg.cell_size*scale);
	    				instance.transform.rotate(0, 1, 0, -90);
	    				instances.add(instance);
	    			}else
	    			if(dg.cells[i+1][j] != 0 && dg.cells[i+1][j] != dg.PERIMETER &&
	    					(dg.cells[i-1][j] == dg.PERIMETER || dg.cells[i-1][j] == 0) &&
	    					dg.cells[i][j+1] != 0 && dg.cells[i][j+1] != dg.PERIMETER &&
	    					dg.cells[i][j-1] != 0 && dg.cells[i][j-1] != dg.PERIMETER ){
	    				instance = new ModelInstance(wallTile); 
	    				instance.transform.translate(i*dg.cell_size*scale,0, -j*dg.cell_size*scale);
	    				instance.transform.rotate(0, 1, 0, 90);
	    				instances.add(instance);
	    			}else
        			/*
        			else
    				if(dg.cells[i+1][j] != 0 &&
    					dg.cells[i-1][j] != 0 &&
    					dg.cells[i][j+1] != 0 &&
    					dg.cells[i][j-1] == dg.PERIMETER ){
	    				instance = new ModelInstance(wallTile); 
	    				instance.transform.setToTranslation(i*dg.cell_size*scale,0, j*dg.cell_size*scale);
	    				instances.add(instance);
	    			}else
    				if(dg.cells[i+1][j] == dg.PERIMETER &&
    					dg.cells[i-1][j] != 0 &&
    					dg.cells[i][j+1] != 0 &&
    					dg.cells[i][j-1] != 0 ){
	    				instance = new ModelInstance(wallTile); 
	    				instance.transform.setToTranslation(i*dg.cell_size*scale,0, j*dg.cell_size*scale);
	    				instance.transform.setToRotation(0, 1, 0, 90);
	    				instances.add(instance);
	    			}else
    				if(dg.cells[i+1][j] != 0 &&
    					dg.cells[i-1][j] == dg.PERIMETER &&
    					dg.cells[i][j+1] != 0 &&
    					dg.cells[i][j-1] != 0 ){
	    				instance = new ModelInstance(wallTile); 
	    				instance.transform.setToTranslation(i*dg.cell_size*scale,0, j*dg.cell_size*scale);
	    				instance.transform.setToRotation(0, 1, 0, -90);
	    				instances.add(instance);
	    			}else*/
        						
        			if(dg.cells[i+1][j] != 0 &&
        					dg.cells[i-1][j] != 0 &&
        					(dg.cells[i][j+1] == 0 || dg.cells[i][j+1] == dg.PERIMETER )&&
        					(dg.cells[i][j-1] == 0 || dg.cells[i][j-1] == dg.PERIMETER) ){
        				instance = new ModelInstance(corridorTile); 
        				instance.transform.translate(i*dg.cell_size*scale,0, -j*dg.cell_size*scale);
        				instance.transform.rotate(0, 1, 0, 90);
            			instances.add(instance);
        			}else
        			if((dg.cells[i+1][j] == 0 || dg.cells[i+1][j] == dg.PERIMETER) &&
        					(dg.cells[i-1][j] == 0 || dg.cells[i-1][j] == dg.PERIMETER) &&
        					dg.cells[i][j+1] != 0 &&
        					dg.cells[i][j-1] != 0 ){
        				instance = new ModelInstance(corridorTile); 
        				instance.transform.translate(i*dg.cell_size*scale,0, -j*dg.cell_size*scale);
        				instances.add(instance);
        			}else{
        				instance = new ModelInstance(normalTile); 
        				instance.transform.translate(i*dg.cell_size*scale,0, -j*dg.cell_size*scale);
            			instances.add(instance);  
        			}
        			
        			if(dg.cells[i][j] == dg.STAIR_DN){
        				instance = new ModelInstance(normalTile); 
        				instance.transform.translate(i*dg.cell_size*scale,0.5f, -j*dg.cell_size*scale);
        				instance.transform.scale(0.8f, 0.8f, 0.8f);
        				instance.materials.get(0).set(ColorAttribute.createDiffuse(0.4f, 1.0f, 0.4f, 0.9f));
        				instances.add(instance);
        			}
        			
        			if(dg.cells[i][j] == dg.STAIR_UP){
        				instance = new ModelInstance(normalTile); 
        				instance.transform.translate(i*dg.cell_size*scale,0.5f, -j*dg.cell_size*scale);
        				instance.transform.scale(0.8f, 0.8f, 0.8f);
        				instance.materials.get(0).set(ColorAttribute.createDiffuse(1.0f, 0.4f, 0.4f, 0.9f));
        				instances.add(instance);
        			}
        			
        		}
        		
        	}
        }
        loading = false;
    }
	
	public void act(float dt){
		 if (!loading){
				 //monkeyInstance.transform.setToTranslation(50, 1, 50);
			 camera.lookAt(player.x/10 -3, 0, -player.y/10 + 3);
			 camera.update();
			 monkeyInstance.transform.setToTranslation(player.x/10 -3, 1, -player.y/10 + 3);
			 System.out.println("X: " + player.x + " Y: " + player.y);
		}
	}

	public void draw() {
		 if (loading && assets.update()){
	            doneLoading();
	            System.out.println("DOE LOADING");
		 }
        camController.update();
         
        //Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
 
        modelBatch.begin(camera);
        for (ModelInstance instance : instances){

            modelBatch.render(instance, lights);
            modelBatch.render(monkeyInstance,lights);
        }
        modelBatch.end();
	}
	
}

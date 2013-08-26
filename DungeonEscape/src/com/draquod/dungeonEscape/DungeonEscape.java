package com.draquod.dungeonEscape;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class DungeonEscape implements ApplicationListener {
	private Stage stage;
	private Player player;
	private AssetManager manager = new AssetManager();
	OrthographicCamera cam;
	DungeonGenerator dg;
	Debug_DungeonDrawer ddd;
	ModelBatch mb; 
	Lights lights;
	PerspectiveCamera camera;
	ModelLoader loader;
	Model model;
	CameraInputController camController;
	ModelInstance instance;
	DungeonDrawer dungeonDrawer;
	@Override
	public void create() {		
		
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		manager = new AssetManager();
		
		stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        //World world = new World();
        //Debug_DungeonDrawer2 ddd2 = new Debug_DungeonDrawer2();
        //ddd2.w = world;
        //stage.addActor(ddd2);
        
        
        //world.Debug_PrintWorld();
        ddd = new Debug_DungeonDrawer();
        if(Gdx.app.getType() == ApplicationType.Android){
        	stage.addActor(ddd);
        }
        System.out.println("Begining dungeon creation");
        dg = new DungeonGenerator();
        dg.CreateDungeon();
        System.out.println("Dungeon created :)");
        ddd.dg = dg;
        
        player = new Player();
        stage.addActor(player);
        player.x = dg.begin.x*dg.cell_size*10 + dg.cell_size*10/2;
        player.y = dg.begin.y*dg.cell_size*10 + dg.cell_size*10/2;
        cam = new OrthographicCamera(100, 100);
        if(!Data.DEBUG)  cam.zoom = 0.6f;
        if(Data.DEBUG)  cam.zoom = 3.5f;
		stage.setCamera(cam);
		player.dg = dg;
		
		if(Data.DEBUG)  cam.zoom = 3.5f;cam.position.set(dg.cell_size*10*dg.n_cols/2,dg.cell_size*10*dg.n_rows/2,0);
		//cam.update();
		
		Joystick joystick = new Joystick();
		joystick.player = player;
		stage.addActor(joystick);
		
		if(Gdx.app.getType() == ApplicationType.Desktop || Gdx.app.getType() == ApplicationType.WebGL){
			dungeonDrawer = new DungeonDrawer();
			dungeonDrawer.dg = dg;
			dungeonDrawer.player = player;
			joystick.cam = dungeonDrawer.camera;
		}
		
	}

	@Override
	public void dispose() {
		 stage.dispose();
		 //model.dispose();
		 //mb.dispose();
	}

	@Override
	public void render() {	
		//camController.update();
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
        if(Gdx.app.getType() == ApplicationType.Android){
	        if(Data.DEBUG) cam.position.set(dg.cell_size*10*dg.n_cols/2,dg.cell_size*10*dg.n_rows/2,0);
			if(!Data.DEBUG) cam.position.set(player.x,player.y,0);
        }
        
        if(Gdx.app.getType() == ApplicationType.Desktop || Gdx.app.getType() == ApplicationType.WebGL){
        	
	        dungeonDrawer.act(Gdx.graphics.getDeltaTime());
			dungeonDrawer.draw();
        }
        
	    cam.position.set(player.x,player.y,0);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		
		
		if(dg.cells[(int) player.x/dg.cell_size/10][(int) player.y/dg.cell_size/10] == dg.STAIR_UP){
			dg.CreateDungeon();
			ddd.dg = dg;
			if(Gdx.app.getType() == ApplicationType.Desktop || Gdx.app.getType() == ApplicationType.WebGL){
				dungeonDrawer.doneLoading();
			}
			player.x = dg.begin.x*dg.cell_size*10 + dg.cell_size*10/2;
	        player.y = dg.begin.y*dg.cell_size*10 + dg.cell_size*10/2;
			
		}
		
		
		/*
		
		if(dg.cells[(int) player.x/dg.cell_size/10][(int) player.y/dg.cell_size/10] == dg.STAIR_UP){
			dg.CreateDungeon();
			ddd.dg = dg;
			player.x = dg.begin.x*dg.cell_size*10 + dg.cell_size*10/2;
	        player.y = dg.begin.y*dg.cell_size*10 + dg.cell_size*10/2;
			
		}
		if(Data.DEBUG) cam.position.set(dg.cell_size*10*dg.n_cols/2,dg.cell_size*10*dg.n_rows/2,0);
		if(!Data.DEBUG) cam.position.set(player.x,player.y,0);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		*/

	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(800, 480, false);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}

package com.draquod.dungeonEscape;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class DungeonEscape implements ApplicationListener {
	private Stage stage;
	private Player player;
	private AssetManager manager = new AssetManager();
	OrthographicCamera cam;
	DungeonGenerator dg;
	 Debug_DungeonDrawer ddd;
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
        stage.addActor(ddd);
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
	}

	@Override
	public void dispose() {
		 stage.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
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

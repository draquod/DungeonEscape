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
	private AssetManager manager = new AssetManager();
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		manager = new AssetManager();
		
		stage = new Stage();
        Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void dispose() {
		 stage.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

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

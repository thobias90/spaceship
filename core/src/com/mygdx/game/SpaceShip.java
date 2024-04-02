package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;;

public class SpaceShip extends ApplicationAdapter {
	//The SpriteBatch is a special class that is used to draw 2D images, like the textures we loaded.
	SpriteBatch background;
	Texture bgImg, tShip;
	private Sprite spaceShip;
	
	/**
	 * Cria os elementos
	 */
	@Override
	public void create () {
		background = new SpriteBatch();
		bgImg = new Texture("bg.png");
		tShip = new Texture("spaceship.png");
		spaceShip = new Sprite(tShip);
	}

	/**
	 * Loop principal
	 */
	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		background.begin();
		background.draw(bgImg, 0, 0);
		background.draw(spaceShip, 0, 0);
		background.end();
	}
	
	/**
	 * Limpa o que criou
	 */
	@Override
	public void dispose () {
		background.dispose();
		bgImg.dispose();
		tShip.dispose();
	}
}

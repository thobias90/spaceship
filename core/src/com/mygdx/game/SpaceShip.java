package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SpaceShip extends ApplicationAdapter {
	SpriteBatch background;
	Texture bgImg;
	
	/**
	 * Cria os elementos
	 */
	@Override
	public void create () {
		background = new SpriteBatch();
		bgImg = new Texture("bg.png");
	}

	/**
	 * Loop principal
	 */
	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		background.begin();
		background.draw(bgImg, 0, 0);
		background.end();
	}
	
	/**
	 * Limpa o que criou
	 */
	@Override
	public void dispose () {
		background.dispose();
		bgImg.dispose();
	}
}

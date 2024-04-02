package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class SpaceShip extends ApplicationAdapter {
	// The SpriteBatch is a special class that is used to draw 2D images, like the textures we loaded.
	SpriteBatch background;
	Texture bgImg, tShip;
	private Sprite spaceShip;
	private float spaceShipPositionX, spaceShipPositionY;
	private int speed = 10;
	
	/**
	 * Create elements
	 */
	@Override
	public void create () {
		background = new SpriteBatch();
		bgImg = new Texture("bg.png");
		tShip = new Texture("spaceship.png");
		spaceShip = new Sprite(tShip);
		spaceShipPositionX = 0;
		spaceShipPositionY = 0;
	}

	/**
	 * Main loop
	 */
	@Override
	public void render () {
		this.moveSpaceShip();
		ScreenUtils.clear(1, 0, 0, 1);
		background.begin();
		background.draw(bgImg, 0, 0);
		background.draw(spaceShip, spaceShipPositionX, spaceShipPositionY);
		background.end();
	}
	
	/**
	 * Clear resources
	 */
	@Override
	public void dispose () {
		background.dispose();
		bgImg.dispose();
		tShip.dispose();
	}

	/**
	 * Move spaceship
	 */
	private void moveSpaceShip() {
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (spaceShipPositionX < (Gdx.graphics.getWidth() - spaceShip.getWidth())) {
				spaceShipPositionX += speed;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (spaceShipPositionX > 0) {
				spaceShipPositionX -= speed;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (spaceShipPositionY < (Gdx.graphics.getHeight() - spaceShip.getHeight())) {
				spaceShipPositionY += speed;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (spaceShipPositionY > 0) {
				spaceShipPositionY -= speed;
			}
		}
	}
}

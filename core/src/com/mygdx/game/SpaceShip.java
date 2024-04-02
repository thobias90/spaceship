package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class SpaceShip extends ApplicationAdapter {
	// The SpriteBatch is a special class that is used to draw 2D images, like the
	// textures we loaded.
	SpriteBatch background;
	Texture bgImg, tShip, tMissile;
	private Sprite spaceShip, missile;
	private float spaceShipPosX, spaceShipPosY, missilePosX, missilePosY;
	private int speed = 10;
	private boolean attack;
	private int attackSpeed = 20;

	/**
	 * Create elements
	 */
	@Override
	public void create() {
		background = new SpriteBatch();
		bgImg = new Texture("bg.png");
		tShip = new Texture("spaceship.png");
		spaceShip = new Sprite(tShip);
		tMissile = new Texture("missile.png");
		missile = new Sprite(tMissile);
		spaceShipPosX = 0;
		spaceShipPosY = 0;
		missilePosX = 0;
		missilePosY = 0;
		attack = false;
	}

	/**
	 * Main loop
	 */
	@Override
	public void render() {
		this.moveSpaceShip();
		this.moveMissile();
		ScreenUtils.clear(1, 0, 0, 1);
		background.begin();
		background.draw(bgImg, 0, 0);
		if (attack) {
			background.draw(missile, missilePosX, missilePosY);
		}
		background.draw(spaceShip, spaceShipPosX, spaceShipPosY);
		background.end();
	}

	/**
	 * Clear resources
	 */
	@Override
	public void dispose() {
		background.dispose();
		bgImg.dispose();
		tShip.dispose();
	}

	/**
	 * Move spaceship
	 */
	private void moveSpaceShip() {
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (spaceShipPosX < (Gdx.graphics.getWidth() - spaceShip.getWidth())) {
				spaceShipPosX += speed;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (spaceShipPosX > 0) {
				spaceShipPosX -= speed;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (spaceShipPosY < (Gdx.graphics.getHeight() - spaceShip.getHeight())) {
				spaceShipPosY += speed;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (spaceShipPosY > 0) {
				spaceShipPosY -= speed;
			}
		}
	}

	private void moveMissile() {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !attack ) {
			attack = true;
			missilePosY = spaceShipPosY + (spaceShip.getHeight() / 2) - (missile.getHeight() / 2);
		}
		if (attack) {
			if (missilePosX < Gdx.graphics.getWidth()) {
				missilePosX += attackSpeed;
			} else {
				missilePosX = spaceShipPosX + (spaceShip.getWidth() / 2) - (missile.getWidth() / 2);
				attack = false;
			}
		} else {
			missilePosX = spaceShipPosX + (spaceShip.getWidth() / 2) - (missile.getWidth() / 2);
			missilePosY = spaceShipPosY + (spaceShip.getHeight() / 2) - (missile.getHeight() / 2);
		}
	}
}

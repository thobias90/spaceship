package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;

public class SpaceShip extends ApplicationAdapter {
	// The SpriteBatch is a special class that is used to draw 2D images, like the
	// textures we loaded.
	private SpriteBatch background;
	private Texture bgImg, tShip, tMissile, tEnemy;
	private Sprite spaceShip, missile;
	private float spaceShipPosX, spaceShipPosY, missilePosX, missilePosY;
	private int speed = 10;
	private boolean attack, gameOver, start;
	private int attackSpeed = 20;
	private Array<Rectangle> enemies;
	private int enemySpeed = 7;
	private long lastEnemyTime;
	private long enemySpawnTime = 750; // Time in ms
	private int score;
	private int life = 3;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	private BitmapFont bitmap;

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
		tEnemy = new Texture("enemy.png");
		enemies = new Array<Rectangle>();
		spaceShipPosX = 0;
		spaceShipPosY = 0;
		missilePosX = 0;
		missilePosY = 0;
		attack = false;
		lastEnemyTime = 0;
		score = 0;
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 30;
		parameter.borderWidth = 1;
		parameter.borderColor = Color.BLACK;
		parameter.color = Color.WHITE;
		bitmap = generator.generateFont(parameter);
		gameOver = false;
		start = false;
	}

	/**
	 * Main loop
	 */
	@Override
	public void render() {
		if (!gameOver) {
			this.moveSpaceShip();
			this.moveMissile();
			this.moveEnemies();
		}
		ScreenUtils.clear(1, 0, 0, 1);
		background.begin();
		background.draw(bgImg, 0, 0);
		if (!start) {
			bitmap.draw(background, "PRESS ENTER TO START", (Gdx.graphics.getWidth() / 2) - 210, (Gdx.graphics.getHeight() / 2));
			if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
				start = true;
			}
		} else {
			if (!gameOver) {
				if (attack) {
					background.draw(missile, missilePosX, missilePosY);
				}
				background.draw(spaceShip, spaceShipPosX, spaceShipPosY);
				for (Rectangle enemy : enemies) {
					background.draw(tEnemy, enemy.x, enemy.y);
				}
				bitmap.draw(background, "Score: " + score, 20, (Gdx.graphics.getHeight() - 20));
				bitmap.draw(background, "Life: " + life, (Gdx.graphics.getWidth() - 100), (Gdx.graphics.getHeight() - 20));
			} else {
				bitmap.draw(background, "Score: " + score, 20, (Gdx.graphics.getHeight() - 20));
				bitmap.draw(background, "GAME OVER", (Gdx.graphics.getWidth() / 2) - 90, (Gdx.graphics.getHeight() / 2));
				bitmap.draw(background, "PRESS ENTER TO RESTART", (Gdx.graphics.getWidth() / 2) - 210, (Gdx.graphics.getHeight() / 2)-30);
				if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
					score = 0;
					life = 3;
					spaceShipPosX = 0;
					spaceShipPosY = 0;
					gameOver = false;
					enemies.clear();
					enemySpawnTime= 750;
				}
			}
		}
		

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
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !attack) {
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

	private void spawnEnemies() {
		Rectangle enemy = new Rectangle(
				Gdx.graphics.getWidth(),
				(MathUtils.random(0, Gdx.graphics.getHeight() - tEnemy.getHeight())),
				tEnemy.getWidth(),
				tEnemy.getHeight());
		enemies.add(enemy);
		lastEnemyTime = TimeUtils.millis();
	}

	private void moveEnemies() {
		if ((TimeUtils.millis() - lastEnemyTime) > enemySpawnTime) {
			this.spawnEnemies();
		}
		for (Iterator<Rectangle> iter = enemies.iterator(); iter.hasNext();) {
			Rectangle enemy = iter.next();
			enemy.x -= enemySpeed;

			if (this.collide(enemy.x, enemy.y, enemy.getWidth(), enemy.getHeight(), missilePosX, missilePosY,
					missile.getWidth(), missile.getHeight())) {
				score++;
				if (score % 10 == 0) {
					enemySpawnTime -= 100;
				}
				attack = false;
				iter.remove();
			} else if (this.collide(enemy.x, enemy.y, enemy.getWidth(), enemy.getHeight(), spaceShipPosX, spaceShipPosY,
					spaceShip.getWidth(), spaceShip.getHeight()) && !gameOver) {
				if (life > 0) {
					life--;
				} else {
					gameOver = true;
				}
				iter.remove();
			}

			if (enemy.x + tEnemy.getWidth() < 0) {
				iter.remove();
			}
		}
	}

	private boolean collide(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
		if (x1 + w1 > x2 && x1 < x2 + w2 && y1 + h1 > y2 && y1 < y2 + h2) {
			return true;
		}
		return false;
	}
}
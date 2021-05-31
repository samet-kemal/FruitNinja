package com.samet.fruitgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

public class fruitNinja extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Texture background;
	Texture apple;
	Texture bill;
	Texture cherry;
	Texture ruby;

	BitmapFont font;
	FreeTypeFontGenerator fontGenerator;

	Random random = new Random();
	Array<Fruit> fruitArray = new Array<Fruit>();


	private final float startGenSpeed = 1.1f;
	int score = 0;
	int lives = 0;
	float genCounter = -9.1f;
	float genSpeed = startGenSpeed;

	private double currentTime;
	private double gameOverTime = -1.0f;

	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("back.png");
		apple = new Texture("apple.png");
		bill = new Texture("bill.png");
		cherry = new Texture("cherry.png");
		ruby = new Texture("ruby.png");
		Fruit.radius = Math.max(Gdx.graphics.getHeight(), Gdx.graphics.getWidth()) / 20f;
		Gdx.input.setInputProcessor(this);

		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Square One Bold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params.color = Color.BLUE;
		params.size = 50;
		params.characters = "0123456789 CUTOPLAYSRE:.+-";
		font = fontGenerator.generateFont(params);
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		double newTime = TimeUtils.millis() / 1000.0;
		System.out.println("newTime : " + newTime);
		double frameTime = Math.min(newTime - currentTime, 0.3);
		System.out.println("frame Time :" + frameTime);

		float deltaTime = (float) frameTime;
		System.out.println("deltaTime :" + deltaTime);
		currentTime = newTime;


		if (lives <= 0 && gameOverTime == 0f) {

			//GAME OVER
			gameOverTime = currentTime;

		}
		if (lives > 0) {

			//GAME MODE

			genSpeed -= deltaTime * 0.0015;
			System.out.println("genSpeed :" + genSpeed);
			System.out.println("genCounter:" + genCounter);

			if (genCounter <= 0f) {
				genCounter = genSpeed * 1.2f;
				addItem();
			} else {
				genCounter -= deltaTime;
			}


			for (int i = 0; i < lives; i++) {
				batch.draw(apple, i * 30f + 20f, Gdx.graphics.getHeight() - 25f, 25f, 25f);
			}

			for (Fruit fruit : fruitArray) {
				fruit.update(deltaTime);

				switch (fruit.type) {
					case REGULAR:
						batch.draw(apple, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
						break;
					case EXTRA:
						batch.draw(cherry, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
						break;
					case ENEMY:
						batch.draw(ruby, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
						break;
					case LIFE:
						batch.draw(bill, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
						break;

				}


			}

		}
		font.draw(batch, "SCORE : " + score, 30, 40);
		if (lives <= 0) {
			font.draw(batch, "CUT TO PLAY", Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.5f);

		}
		batch.end();
	}

	private void addItem() {

		float pos = random.nextFloat() * Math.max(Gdx.graphics.getHeight(), Gdx.graphics.getWidth());

		Fruit item = new Fruit(new Vector2(pos, -Fruit.radius), new Vector2((Gdx.graphics.getWidth() * 0.5f - pos) * 0.3f + (random.nextFloat() - 0.5f), Gdx.graphics.getHeight() * 0.5f));

		float type = random.nextFloat();
		if (type > 0.98) {
			item.type = Fruit.Type.LIFE;
		} else if (type > 0.88) {
			item.type = Fruit.Type.EXTRA;
		} else if (type > 0.78) {
			item.type = Fruit.Type.ENEMY;
		}

		fruitArray.add(item);

	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		fontGenerator.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		//menu Mode
		if (lives <= 0 && currentTime - gameOverTime > 2f) {
			gameOverTime = 0f;
			score = 0;
			lives = 4;
			genSpeed = startGenSpeed;
			fruitArray.clear();

		} else {
			// Game Mode
			Array<Fruit> toRemove = new Array<Fruit>();
			Vector2 pos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
			int plusScore = 0;
			for (Fruit f : fruitArray) {
				System.out.println("distance:" + pos.dst2(f.pos));
				System.out.println("distance:" + f.clicked(pos));
				System.out.println("distance:" + Fruit.radius * Fruit.radius + 1);


				if (f.clicked(pos)) {
					toRemove.add(f);
					switch (f.type) {
						case REGULAR:
							plusScore++;
							break;
						case EXTRA:
							plusScore += 2;
							score++;
							break;
						case ENEMY:
							lives--;
							break;
						case LIFE:
							lives++;
							break;
					}
				}

			}
			score += plusScore * plusScore;
			for (Fruit f : toRemove) {
				fruitArray.removeValue(f, true);

			}


		}

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}

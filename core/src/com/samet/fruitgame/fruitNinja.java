package com.samet.fruitgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class fruitNinja extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Texture background;
	Texture apple;
	Texture bill;
	Texture cherry;
	Texture ruby;

	BitmapFont font;
	FreeTypeFontGenerator fontGenerator;

	int lives=4;
	int score=0;

	private  double currentTime;
	private  double gameOverTime= -1.0f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("back.png");
		apple = new Texture("apple.png");
		bill = new Texture("bill.png");
		cherry = new Texture("cherry.png");
		ruby = new Texture("ruby.png");

		Gdx.input.setInputProcessor(this);
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Square One Bold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params.color = Color.BLUE;
		params.size = 50;
		params.characters ="0123456789 CUTOPLAYSRE:.+-";
		font = fontGenerator.generateFont(params);
	}

	@Override
	public void render () {
		batch.begin();

		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		double newTime = TimeUtils.millis() / 1000.0;
		System.out.println("newTime : "+ newTime);
		double frameTime = Math.min(newTime - currentTime,0.3) ;
		System.out.println("frame Time :" + frameTime);

		float deltaTime = (float) frameTime;
		System.out.println("deltaTime :" + deltaTime);
		currentTime = newTime;

		if (lives <=0 && gameOverTime == 0f){

			//GAME OVER
			gameOverTime = currentTime;


		}
		if (lives >0){
			//GAME MODE
			for(int i =0; i<lives;i++){
				batch.draw(apple,i*30f+20f,Gdx.graphics.getHeight()-25f,25f,25f);
			}


		}
		font.draw(batch,"SCORE : 0",30,40);
		font.draw(batch,"CUT TO PLAY",Gdx.graphics.getWidth()*0.5f,Gdx.graphics.getHeight()*0.5f);
		batch.end();
	}
	
	@Override
	public void dispose () {
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

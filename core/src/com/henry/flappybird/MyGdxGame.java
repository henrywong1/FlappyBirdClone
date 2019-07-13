package com.henry.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.Console;

import javax.xml.soap.Text;

import sun.rmi.runtime.Log;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;

	Texture background;
	Texture[] bird;
	Texture topTube;
	Texture botTube;

	float gravity = 0.2f;
	float velocity = 0;

	int birdState = 0;
	int score = 0;
	int pause = 0;
	int birdY = 0;


	@Override
	public void create () {
		batch = new SpriteBatch();
		bird = new Texture[2];
		bird[0] = new Texture("bird.png");
		bird[1] = new Texture("bird2.png");
		topTube = new Texture("toptube.png");
		botTube = new Texture("bottomtube.png");
		background = new Texture("bg.png");
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		if (Gdx.input.justTouched()) {
			velocity = -10;
		}

		if (pause < 12) {
			pause++;
		} else {
			pause = 0;
			if (birdState < 1) {
				birdState++;
			} else {
				birdState = 0;
			}
		}
		velocity += gravity;
		birdY -= velocity;
		if (birdY <= 0) {
			birdY = 0;
		}
		batch.draw(bird[birdState], Gdx.graphics.getWidth() / 2 - bird[birdState].getWidth(), Gdx.graphics.getHeight() / 2 - bird[birdState].getHeight());

		batch.end();
	}

}

package com.henry.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.Console;
import java.util.Random;

import javax.xml.soap.Text;

import sun.rmi.runtime.Log;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;

	Texture background;
	Texture[] bird;
	Texture topTube;
	Texture botTube;

	float gravity = 2f;
	float velocity = 0;
	float gap = 475;
	float tubeOffset;

	float tubeVelocity = 4f;
	float tubeDistance;
	int numOfTubes = 4;
	float[] tubeX = new float[numOfTubes];
	float[] tempTubeOffset = new float[numOfTubes];
	Random rand;

	int birdState = 0;
	int score = 0;
	int pause = 0;
	int birdY = 0;
	int gameState = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		bird = new Texture[2];
		bird[0] = new Texture("bird.png");
		bird[1] = new Texture("bird2.png");
		topTube = new Texture("toptube.png");
		botTube = new Texture("bottomtube.png");
		background = new Texture("bg.png");

		tubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;

		birdY = Gdx.graphics.getHeight() / 2 - bird[0].getHeight();

		rand = new Random();
		tubeDistance = Gdx.graphics.getWidth() / 2;

		for (int i = 0; i < numOfTubes; i++) {
			tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + i * tubeDistance;
			tempTubeOffset[i] = (rand.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 780);
		}

	}


	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState != 0 ) {
			if (Gdx.input.justTouched()) {

				velocity = -35;

			}
			for (int i = 0; i < numOfTubes; i++) {

				if (tubeX[i] < - topTube.getWidth()) {

					tubeX[i] += numOfTubes * tubeDistance;
					tempTubeOffset[i] = (rand.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 780);

				}

				tubeX[i] = tubeX[i] - tubeVelocity;

				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2  + gap / 2 + tempTubeOffset[i]);
				batch.draw(botTube, tubeX[i], Gdx.graphics.getHeight() / 2  - gap / 2 - botTube.getHeight() + tempTubeOffset[i]);
			}


			if (birdY > 0 || velocity < 0) {
				velocity += gravity;
				birdY -= velocity;
			}


		} else {
			if (Gdx.input.justTouched()) {

				gameState = 1;
			}
		}

		if (birdState == 0) {
			birdState = 1;
		} else {
			birdState = 0;
		}





		batch.draw(bird[birdState], Gdx.graphics.getWidth() / 2 - bird[birdState].getWidth() / 2, birdY);
		batch.end();

	}

}

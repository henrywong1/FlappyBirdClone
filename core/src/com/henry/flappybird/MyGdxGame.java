package com.henry.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.awt.Color;
import java.io.Console;
import java.util.Random;

import javax.xml.soap.Text;

import sun.rmi.runtime.Log;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;

	ShapeRenderer shapeRenderer;

	Circle birdCircle;

	Rectangle[] topRect;
	Rectangle[] botRect;

	BitmapFont font;

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
	int scoreTube = 0;
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

		topRect = new Rectangle[numOfTubes];
		botRect = new Rectangle[numOfTubes];

		for (int i = 0; i < numOfTubes; i++) {
			tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * tubeDistance;
			tempTubeOffset[i] = (rand.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 780);

			topRect[i] = new Rectangle();
			botRect[i] = new Rectangle();

		}
		shapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();
		font = new BitmapFont();
		font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
		font.getData().setScale(10);

	}


	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1 ) {

			if (tubeX[scoreTube] < Gdx.graphics.getWidth() / 2) {

				score++;

				Gdx.app.log("SCORE", Integer.toString(score));

				if (scoreTube < numOfTubes - 1) {
					scoreTube++;
				} else {
					scoreTube = 0;
				}
			}

			if (Gdx.input.justTouched()) {

				velocity = -35;

			}
			for (int i = 0; i < numOfTubes; i++) {

				if (tubeX[i] < - topTube.getWidth()) {

					tubeX[i] += numOfTubes * tubeDistance;
					tempTubeOffset[i] = (rand.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 780);

				} else {
					tubeX[i] = tubeX[i] - tubeVelocity;


				}

				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2  + gap / 2 + tempTubeOffset[i]);
				batch.draw(botTube, tubeX[i], Gdx.graphics.getHeight() / 2  - gap / 2 - botTube.getHeight() + tempTubeOffset[i]);

				topRect[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tempTubeOffset[i], topTube.getWidth(), topTube.getHeight());
				botRect[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2  - gap / 2 - botTube.getHeight() + tempTubeOffset[i], botTube.getWidth(), botTube.getHeight());

			}


			if (birdY > 0 || velocity < 0) {
				velocity += gravity;
				birdY -= velocity;
			}

		} else if (gameState == 0){
			if (Gdx.input.justTouched()) {

				gameState = 1;

			}
		} else if (gameState == 2) {
			if (Gdx.input.justTouched()) {
				gameState = 1;
				score = 0;
				birdY = Gdx.graphics.getHeight() / 2 - bird[0].getHeight() / 2;
				for (int i = 0; i < numOfTubes; i++) {
					tempTubeOffset[i] = (rand.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 780);
					tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * tubeDistance;
					topRect[i] = new Rectangle();
					botRect[i] = new Rectangle();
				}
				scoreTube = 0;
				velocity = 0;
			}
		}

		if (birdState == 0) {
			birdState = 1;
		} else {
			birdState = 0;
		}




		batch.draw(bird[birdState], Gdx.graphics.getWidth() / 2 - bird[birdState].getWidth() / 2, birdY);
		font.draw(batch, Integer.toString(score), 100 ,200);




		birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + bird[birdState].getHeight() / 2, bird[birdState].getWidth() / 2);


//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
//		shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

		for (int i = 0; i < numOfTubes; i++) {
//
//			shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tempTubeOffset[i], topTube.getWidth(), topTube.getHeight());
//			shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - botTube.getHeight() + tempTubeOffset[i], botTube.getWidth(), botTube.getHeight());

			if (Intersector.overlaps(birdCircle, topRect[i]) || Intersector.overlaps(birdCircle, botRect[i])) {

				gameState = 2;
			}

		}
		batch.end();
	}

}

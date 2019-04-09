package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	SoundManager soundManager;
	Ball ball;
	Paddle paddle;
	BitmapFont font;

	@Override
	public void create() {
		soundManager = new SoundManager();
		soundManager.loadSounds();
		ball = new Ball();
		ball.importTexture();
		paddle = new Paddle();
		paddle.importTexture();
		batch = new SpriteBatch();
		paddle.x = (Gdx.graphics.getWidth() - paddle.texture.getWidth()) / 2;
		ball.reset(paddle);
		font = new BitmapFont();
	}

	@Override
	public void render() {
		paddle.move();
		ball.move(paddle);
		if (collideBall()) {
			soundManager.playRandomBounceSound();
		}
		// losing the ball
		if (ball.y < -ball.texture.getHeight()) {
			soundManager.loseBallSound.play();
			ball.reset(paddle);
		}
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		ball.draw (batch);
		paddle.draw (batch);
		font.draw(batch, "Score: 0", 0, Gdx.graphics.getHeight());
		batch.end();
	}


	@Override
	public void dispose() {
		batch.dispose();
		paddle.texture.dispose();
		soundManager.dispose ();
		ball.dispose ();
		paddle.dispose();
		font.dispose();
	}

	public boolean collideBall() {
		// the ball collides with the right wall
		if (ball.x + ball.texture.getWidth() > Gdx.graphics.getWidth()) {
			ball.velocityX = -ball.velocityX;
			return true;
		}
		// the ball collides with the top wall
		if (ball.y + ball.texture.getHeight() > Gdx.graphics.getHeight()) {
			ball.velocityY = -ball.velocityY;
			return true;
		}
		// the ball collides with the left wall
		if (ball.x < 0) {
			ball.velocityX = -ball.velocityX;
			return true;
		}
		//the ball collides with the top of the paddle
		if (paddle.x - ball.texture.getWidth() / 2 < ball.x && ball.x < paddle.x + paddle.texture.getWidth() - ball.texture.getWidth() / 2) {
			if (ball.y < paddle.y + paddle.texture.getHeight() && ball.y > 0) {
				ball.velocityY = -ball.velocityY;
				return true;
			}
		}
		//The ball collides with the left side of the paddle
		if (ball.x > paddle.x - ball.texture.getWidth() && ball.x < paddle.x - ball.texture.getWidth() / 2 + 1) {
			if (ball.y < paddle.y + paddle.texture.getHeight()) {
				if (ball.velocityX > 0) {
					ball.velocityX = -ball.velocityX;
					return true;
				}
			}
		}
		//The ball collides with the right side of the paddle
		if (ball.x > paddle.x + paddle.texture.getWidth() - ball.texture.getWidth() / 2 - 1
				&& ball.x < paddle.x + paddle.texture.getWidth()) {
			if (ball.y < paddle.y + paddle.texture.getHeight()) {
				if (ball.velocityX < 0) {
					ball.velocityX = -ball.velocityX;
					return true;
				}
			}
		}
		return false;
	}
}
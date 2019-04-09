package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball {
    Texture texture;
    int x, y, velocityX = 10, velocityY = 10;
    int ballStartFrameCounter;
    final int FRAMES_TO_WAIT_BEFORE_BALL_STARTS = 60;

    void importTexture ()
    {
        texture = new Texture("ball_small.png");
    }
    void draw (SpriteBatch batch)
    {
        batch.draw(texture, x, y);
    }
    void move(Paddle paddle) {
        ballStartFrameCounter++;
        //the ball flies
        if (ballStartFrameCounter > FRAMES_TO_WAIT_BEFORE_BALL_STARTS) {
            x += velocityX;
            y += velocityY;
        } else { // if the ball doesn't fly yet, moves along with the paddle
            centerOnPaddle(paddle);
        }
    }
    void reset(Paddle paddle) {
        centerOnPaddle(paddle);
        y = paddle.texture.getHeight() + paddle.y;
        ballStartFrameCounter = 0;
        velocityY = Math.abs(velocityY);
        velocityX = Math.abs(velocityX);
    }
    void centerOnPaddle(Paddle paddle) {
        x = paddle.x + paddle.texture.getWidth() / 2 - texture.getWidth() / 2;
    }
    void dispose ()
    {
        texture.dispose();
    }
}

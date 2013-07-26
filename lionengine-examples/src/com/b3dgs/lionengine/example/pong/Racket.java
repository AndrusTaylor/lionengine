package com.b3dgs.lionengine.example.pong;

import java.awt.Color;

import com.b3dgs.lionengine.Graphic;
import com.b3dgs.lionengine.Timing;
import com.b3dgs.lionengine.game.CameraGame;
import com.b3dgs.lionengine.game.entity.EntityGame;
import com.b3dgs.lionengine.input.Keyboard;
import com.b3dgs.lionengine.utility.UtilityRandom;

/**
 * Racket implementation using EntityGame base.
 */
public class Racket
        extends EntityGame
{
    /** Racket width. */
    private static final int WIDTH = 4;
    /** Racket height. */
    private static final int HEIGHT = 24;
    /** Screen width. */
    private final int screenWidth;
    /** Screen height. */
    private final int screenHeight;
    /** Movement handler. */
    private final boolean automatic;
    /** Increase key. */
    private final Integer decrease;
    /** Decrease key. */
    private final Integer increase;
    /** Random movement tiler. */
    private final Timing timerRandomMovement;
    /** Racket speed. */
    private double speed;
    /** Racket last random speed. */
    private double speedLastRandom;

    /**
     * Constructor.
     * 
     * @param screenWidth The screen width.
     * @param screenHeight The screen height.
     * @param x The horizontal location.
     * @param y The vertical location.
     * @param automatic <code>true</code> for an automatic movement, <code>false</code> for a manual movement.
     */
    public Racket(int screenWidth, int screenHeight, int x, int y, boolean automatic)
    {
        super();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.automatic = automatic;
        timerRandomMovement = new Timing();
        decrease = Keyboard.UP;
        increase = Keyboard.DOWN;
        setSize(Racket.WIDTH, Racket.HEIGHT);
        setLocation(x, y);
        timerRandomMovement.start();
    }

    /**
     * Update and control the racket.
     * 
     * @param extrp The extrapolation value.
     * @param keyboard The keyboard reference.
     * @param ball The ball reference.
     */
    public void update(double extrp, Keyboard keyboard, Ball ball)
    {
        // Update the movement
        if (automatic)
        {
            updateAutomatic(extrp, ball);
        }
        else
        {
            updateManual(extrp, keyboard);
        }

        // Block on border
        if (getLocationY() < 0.0 + getHeight() / 2.0)
        {
            setLocationY(0.0 + getHeight() / 2.0);
        }
        if (getLocationY() > screenHeight - getHeight() / 2.0)
        {
            setLocationY(screenHeight - getHeight() / 2.0);
        }

        // Update collisions
        updateCollision(-getWidth() / 2, -getHeight() / 2, getWidth(), getHeight());
    }

    /**
     * Render the racket on screen.
     * 
     * @param g The graphics output.
     * @param camera The camera reference.
     */
    public void render(Graphic g, CameraGame camera)
    {
        final int x = camera.getViewpointX(getLocationIntX() - getWidth() / 2);
        final int y = camera.getViewpointY(getLocationIntY() + getHeight() - getHeight() / 2);
        g.setColor(Color.CYAN);
        g.drawRect(x, y, getWidth(), getHeight(), true);
    }

    /**
     * Set the racket movement speed.
     * 
     * @param speed The movement speed.
     */
    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    /**
     * Update automatic movement.
     * 
     * @param extrp The extrapolation value.
     * @param ball The ball reference.
     */
    private void updateAutomatic(double extrp, Ball ball)
    {
        double speed = 0.0;
        // Do random movement if ball is to far
        final double distance = getLocationX() - ball.getLocationX();
        if (distance > screenWidth / 1.7)
        {
            if (timerRandomMovement.elapsed(200))
            {
                speedLastRandom = (0.5 - UtilityRandom.getRandomDouble()) * 1.5 * this.speed;
                timerRandomMovement.start();
            }
            speed = speedLastRandom;
        }
        // Try to be in front of the ball
        else
        {
            if (ball.getLocationY() > getLocationY() + getHeight() / 3.0 - 1)
            {
                speed = this.speed;
            }
            if (ball.getLocationY() < getLocationY() - getHeight() / 3.0 + 1)
            {
                speed = -this.speed;
            }
        }
        moveLocation(extrp, 0.0, speed);
    }

    /**
     * Update the manual movement.
     * 
     * @param extrp The extrapolation value.
     * @param keyboard The keyboard reference.
     */
    private void updateManual(double extrp, Keyboard keyboard)
    {
        // Get the speed
        double speed = 0.0;
        if (keyboard.isPressed(increase))
        {
            speed = this.speed;
        }
        if (keyboard.isPressed(decrease))
        {
            speed = -this.speed;
        }

        // Apply movement
        this.moveLocation(extrp, 0.0, speed);
    }
}

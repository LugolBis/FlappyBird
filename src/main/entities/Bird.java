package main.entities;

import main.entities.Move;

public class Bird implements Move {
    protected float x;
    protected float y;
    protected float radius;
    protected boolean alive;

    public Bird(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.alive = true;
    }

    public boolean getAlive() {
        return this.alive;
    }

    public void updateCoord(float xMove, float yMove) {
        // We never change the position of the bird, this is the background who's move
        this.y += yMove;
    }
}
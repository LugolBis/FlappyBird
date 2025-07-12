package com.lugolbis.entities;

public class Bird implements Entity {
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

    public void updateCoord(float xEntity, float yEntity) {
        // We never change the position of the bird, this is the background who's move
        this.y += yEntity;
    }
}
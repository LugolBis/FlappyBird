package com.lugolbis.entities;

public class Ground implements Entity {
    protected float coordY;

    public Ground(float y) {
        this.coordY = y;
    }

    public void updateCoord(float xEntity, float yEntity) {
        // This method is empty because the Ground never move.
        // But the class implement this Interface for genericity usages
    }
}
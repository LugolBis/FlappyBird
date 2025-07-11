package main.entities;

import main.entities.Move;

public class Ground implements Move {
    protected float coordY;

    public Ground(float y) {
        this.coordY = y;
    }

    public void updateCoord(float xMove, float yMove) {
        return;
    }
}
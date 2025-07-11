package main.entities;

import main.entities.Move;

public class Pipe implements Move {
    protected float x;
    protected float yTop;
    protected float yBottom;
    protected float radius;

    public Pipe(float x, float yTop, float yBottom, float radius) {
        this.x = x;
        this.yTop = yTop;
        this.yBottom = yBottom;
        this.radius = radius;
    }

    public static Pipe spwan(float[] xInterval, float[] yInterval, float radius) {
        float xLimit0 = xInterval[0] + radius*1.2f;
        float xLimit1 = xInterval[1] - radius*1.2f;
        float xRandom = (float)(Math.random() * (xLimit0 - xLimit1 + 1.0f)) + xLimit1;

        float yLimit0 = yInterval[0] + radius*1.2f;
        float yLimit1 = yInterval[1] - radius*1.2f;
        float yRandom = (float)(Math.random() * (yLimit0 - yLimit1 + 1.0f)) + yLimit1;

        return new Pipe(xRandom, (yRandom - radius*1.2f), (yRandom + radius*0.1f), radius*0.8f);
    }

    public void updateCoord(float xMove, float yMove) {
        this.x += xMove;
        this.yTop += yMove;
        this.yBottom += yMove;
    }
}
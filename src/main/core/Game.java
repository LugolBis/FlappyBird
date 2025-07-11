package main.core;

import java.util.List;
import java.util.ArrayList;

import main.entities.Bird;
import main.entities.Pipe;
import main.entities.Ground;
import main.entities.Move;

public class Game {
    public static void main(float xScreen, float yScreen) {
        float radius = yScreen/6.0f;
        Bird bird = new Bird(radius*1.2f, yScreen/2.0f, radius);
        Ground ground = new Ground(yScreen*0.1f);

        float[] xInterval = {xScreen, xScreen/2.0f};
        float[] yInterval = {yScreen*0.1f, yScreen};
        Pipe pipe = Pipe.spwan(xInterval, yInterval, radius);

        List<Move> gameEntities = new ArrayList<>();
        gameEntities.add(bird);
        gameEntities.add(ground);
        gameEntities.add(pipe);

        boolean runing = true;
        while (runing) {
            /*
                NOT YET IMPLEMENTED
            */
            runing = false;
        }
    }
}
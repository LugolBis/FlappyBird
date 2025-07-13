package com.lugolbis;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class PipeComponent extends Component {
    private Entity topPipe;
    private Entity bottomPipe;
    private Entity scoreZone;

    @Override
    public void onAdded() {
        System.out.println("Pipe spwaned.");
    }

    public void setPipes(Entity top, Entity bottom, Entity score) {
        this.topPipe = top;
        this.bottomPipe = bottom;
        this.scoreZone = score;
    }

    public void move() {
        double movement = -5;
        entity.translateX(movement);
        topPipe.translateX(movement);
        bottomPipe.translateX(movement);
        scoreZone.translateX(movement);
    }

    public void remove() {
        FXGL.getGameWorld().removeEntity(entity);
        FXGL.getGameWorld().removeEntity(topPipe);
        FXGL.getGameWorld().removeEntity(bottomPipe);
        FXGL.getGameWorld().removeEntity(scoreZone);
    }
}
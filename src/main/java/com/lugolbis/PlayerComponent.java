package com.lugolbis;

import com.almasb.fxgl.entity.component.Component;

public class PlayerComponent extends Component {
    private boolean alive = true;

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(entity.getCenter());
    }

    public void flight() {
        entity.translateY(-10);

        if (entity.getRotation() > -15) {
            entity.rotateBy(-1.5);
        }
    }

    public void fall() {
        entity.translateY(30);

        if (entity.getRotation() < 90) {
            entity.rotateBy(4);
        }
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean getAlive() {
        return alive;
    }
}

package com.lugolbis;

import com.almasb.fxgl.entity.component.Component;

public class PlayerComponent extends Component {
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
        entity.translateY(50);

        if (entity.getRotation() < 90) {
            entity.rotateBy(4);
        }
    }
}

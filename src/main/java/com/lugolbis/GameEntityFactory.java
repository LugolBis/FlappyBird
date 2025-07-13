package com.lugolbis;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;

import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class GameEntityFactory implements EntityFactory {

    @Spawns("background")
    public Entity newBackground(SpawnData data) {
        return FXGL.entityBuilder(data)
            .view(new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight(), Color.AQUA))
            .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        double size = FXGL.getAppHeight()/7;

        ImageView view = new ImageView(FXGL.image("bird.png"));
        view.setFitWidth(size);
        view.setFitHeight(size);
        view.setPreserveRatio(false);

        return FXGL.entityBuilder(data)
            .type(EntityType.PLAYER)
            .view(view)
            .bbox(new HitBox(BoundingShape.box(size, size)))
            .with(new PlayerComponent())
            .collidable()
            .build();
    }

    @Spawns("pipe")
    public Entity newPipeSet(SpawnData data) {
        double gap = FXGL.getAppHeight()/3.5;
        double pipeWidth = 80;
        double pipeHeight = FXGL.getAppHeight();
        double x = data.getX();
        double y = data.getY();

        // Entité maître (invisible)
        Entity master = FXGL.entityBuilder(data)
            .type(EntityType.PIPE_SET)
            .with(new PipeComponent())
            .build();

        // Création des parties
        Entity topPipe = createPipePart(x, y - pipeHeight, pipeWidth, pipeHeight, EntityType.PIPE);
        Entity bottomPipe = createPipePart(x, y + gap, pipeWidth, pipeHeight, EntityType.PIPE);
        Entity scoreZone = createScoreZone(x + pipeWidth, y, gap);

        // Lier les parties au composant maître
        master.getComponent(PipeComponent.class).setPipes(topPipe, bottomPipe, scoreZone);
        
        return master;
    }

    private Entity createPipePart(double x, double y, double width, double height, EntityType type) {
        return FXGL.entityBuilder()
            .at(x, y)
            .type(type)
            .viewWithBBox(new Rectangle(width, height, Color.GREEN))
            .collidable()
            .buildAndAttach();
    }

    private Entity createScoreZone(double x, double y, double gap) {
        return FXGL.entityBuilder()
            .at(x, y - gap/2)
            .type(EntityType.SCORE_ZONE)
            .viewWithBBox(new Rectangle(1, gap, Color.TRANSPARENT))
            .collidable()
            .buildAndAttach();
    }
}

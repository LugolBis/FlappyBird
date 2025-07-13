package com.lugolbis;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class App extends GameApplication {

    private Entity player;
    private List<Entity> pipes;
    private int score;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1920);
        settings.setHeight(1080);
        settings.setTitle("Flappy Bird");
        settings.setVersion("");
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.UP, () -> player.getComponent(PlayerComponent.class).flight());
        FXGL.onKey(KeyCode.SPACE, () -> player.getComponent(PlayerComponent.class).flight());
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());
        FXGL.spawn("background");

        player = FXGL.spawn("player", FXGL.getAppWidth()/3, FXGL.getAppHeight()/4);
        FXGL.run(() -> updatePlayer(), Duration.seconds(0.2));

        pipes = new ArrayList<>();
        FXGL.run(() -> {
            double centerY = FXGL.random(150, FXGL.getAppHeight() - 150);
            pipes.add(FXGL.spawn("pipe", FXGL.getAppWidth(), centerY));
        }, Duration.seconds(2));
        FXGL.run(() -> updatePipes(), Duration.seconds(0));
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollisionBegin(EntityType.PLAYER, EntityType.PIPE, (player, pipe) -> {
            System.out.println("Player colide a pipe..");
            player.getComponent(PlayerComponent.class).setAlive(false);
        });
        FXGL.onCollisionBegin(EntityType.PLAYER, EntityType.SCORE_ZONE, (player, score_zone) -> {
            System.out.println("Player colide an object.");
            score++;
            score_zone.removeFromWorld();
        });
    }

    private void updatePipes() {
        Iterator<Entity> iterator = pipes.iterator();

        while (iterator.hasNext()) { 
            Entity entity = iterator.next();

            if (!isVisible(entity)) {
                entity.getComponent(PipeComponent.class).remove();
                System.out.println("Pipe despawned.");
                iterator.remove();
            } else {
                entity.getComponent(PipeComponent.class).move();
            }
        }
    }

    private void updatePlayer() {
        if (!isVisible(player) || !player.getComponent(PlayerComponent.class).getAlive()) {
            System.out.println("visible=" + !isVisible(player));
            System.out.println("alive=" + !player.getComponent(PlayerComponent.class).getAlive());
            System.out.println("Score=" + score);
            player.removeFromWorld();
            FXGL.getGameController().exit();
        }
        else {
            player.getComponent(PlayerComponent.class).fall();
        }
    }

    private boolean isVisible(Entity entity) {
        double width = entity.getBoundingBoxComponent().getWidth();
        double height = entity.getBoundingBoxComponent().getHeight();
        double x = entity.getX();
        double y = entity.getY();

        return x + width >= 0 && 
            x <= FXGL.getAppWidth() && 
            y + height >= 0 && 
            y <= FXGL.getAppHeight();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package com.lugolbis;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class App extends GameApplication {

    private Entity player;
    private List<Entity> pipes;

    private TimerAction pipeSpawnAction;
    private TimerAction pipeUpdateAction;

    private boolean isGameOver;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1920);
        settings.setHeight(1080);
        settings.setTitle("Flappy Bird");
        settings.setVersion("");
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new Menu();
            }
        });
        settings.setFullScreenAllowed(true);
        settings.setManualResizeEnabled(true);
        settings.setMenuKey(KeyCode.ESCAPE);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
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

        isGameOver = false;
        player = FXGL.spawn("player", FXGL.getAppWidth()/3, FXGL.getAppHeight()/4);
        FXGL.run(() -> updatePlayer(), Duration.seconds(0.2));

        pipes = new ArrayList<>();
        pipeSpawnAction = FXGL.run(() -> {
            double centerY = FXGL.random(FXGL.getAppHeight()/3.5, FXGL.getAppHeight() - FXGL.getAppHeight()/3.5);
            pipes.add(FXGL.spawn("pipe", FXGL.getAppWidth(), centerY));
        }, Duration.seconds(2));
        pipeUpdateAction = FXGL.run(() -> updatePipes(), Duration.millis(10));
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollisionBegin(EntityType.PLAYER, EntityType.PIPE, (player, pipe) -> {
            System.out.println("Player colide a pipe..");
            isGameOver = true;
        });
        FXGL.onCollisionBegin(EntityType.PLAYER, EntityType.SCORE_ZONE, (player, score_zone) -> {
            System.out.println("Player colide an object.");
            FXGL.inc("score", 1);
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
        if (!isVisible(player) || isGameOver) {
            pipeSpawnAction.expire();
            pipeUpdateAction.expire();

            FXGL.runOnce(() -> {
                pipes.clear();
                FXGL.getGameController().gotoMainMenu();
                Text text = FXGL.getUIFactoryService().newText("", Color.YELLOW, 40);
                text.textProperty().bind(FXGL.getip("score").asString("Game Over !\nScore : %d"));
                FXGL.getDialogService().showMessageBox(text.getText(), () -> {});
            }, Duration.seconds(0.2));
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

    @Override
    protected void initUI() {
        Text text = FXGL.getUIFactoryService().newText("", Color.YELLOW, 40);
        text.textProperty().bind(FXGL.getip("score").asString("Score : %d"));

        FXGL.getWorldProperties().addListener("score", (prev, now) -> {
            FXGL.animationBuilder()
                .duration(Duration.seconds(0.5))
                .interpolator(Interpolators.BOUNCE.EASE_OUT())
                .repeat(2)
                .autoReverse(true)
                .scale(text)
                .from(new Point2D(1, 1))
                .to(new Point2D(1.2, 1.2))
                .buildAndPlay();
        });

        FXGL.addUINode(text, FXGL.getAppWidth()/2, 50);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package com.lugolbis;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Menu extends FXGLMenu {
    public Menu() {
        super(MenuType.MAIN_MENU);

        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(100));

        Text title = FXGL.getUIFactoryService().newText("FLAPPY BIRD", Color.YELLOW, 46);
        HBox titleContainer = new HBox(title);
        titleContainer.setAlignment(Pos.CENTER);

        Button playButton = new Button("Start new game", this::fireNewGame);

        Button quitButton = new Button("Quit", this::fireExit);

        HBox buttonsContainer = new HBox(20, playButton, quitButton);
        buttonsContainer.setAlignment(Pos.CENTER);
        
        container.getChildren().addAll(titleContainer, buttonsContainer);

        StackPane.setAlignment(container, Pos.CENTER);
        getContentRoot().getChildren().add(container);
    }

    private static class Button extends StackPane {
        public Button(String name, Runnable action) {
            Rectangle bg = new Rectangle(400, 60);
            bg.setStroke(Color.WHITE);

            Text text = FXGL.getUIFactoryService().newText(name, Color.WHITE, 40);

            bg.fillProperty().bind(
                Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK)
            );

            text.fillProperty().bind(
                Bindings.when(hoverProperty()).then(Color.BLACK).otherwise(Color.WHITE)
            );

            setOnMouseClicked(e -> action.run());

            StackPane.setAlignment(text, Pos.CENTER);
            getChildren().addAll(bg, text);
        }
    }
}

package com.lugolbis;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Menu extends FXGLMenu {
    public Menu() {
        super(MenuType.MAIN_MENU);

        VBox container = new VBox(30);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(FXGL.getAppHeight()*0.1, FXGL.getAppWidth()*0.1, FXGL.getAppHeight()*0.1, FXGL.getAppWidth()*0.1));

        Text title = new Text("FLAPPY BIRD");
        title.setFont(Font.font("Arial", FontWeight.BOLD, FXGL.getAppWidth()*0.1));
        title.setFill(Color.GOLD);
        title.setStroke(Color.DARKGOLDENROD);
        title.setStrokeWidth(2);
        title.setEffect(new DropShadow(10, Color.BLACK));

        VBox titleBox = new VBox(20, title);
        titleBox.setAlignment(Pos.CENTER);

        Button playButton = new Button("START GAME", this::fireNewGame);
        Button quitButton = new Button("QUIT GAME", this::fireExit);

        HBox buttonsContainer = new HBox(50, playButton, quitButton);
        buttonsContainer.setAlignment(Pos.CENTER);
        
        container.getChildren().addAll(titleBox, buttonsContainer);

        StackPane.setAlignment(container, Pos.CENTER);
        getContentRoot().getChildren().add(container);
    }

    private static class Button extends StackPane {
        public Button(String name, Runnable action) {
            // Rectangle avec coins arrondis
            Rectangle bg = new Rectangle(FXGL.getAppWidth()*0.23, FXGL.getAppHeight()*0.15);
            bg.setArcHeight(35);
            bg.setArcWidth(35);
            bg.setStroke(Color.GOLD);
            bg.setStrokeWidth(3);

            Text text = new Text(name);
            text.setFont(Font.font("Arial", FontWeight.BOLD, FXGL.getAppWidth()*0.03));
            text.setFill(Color.WHITE);

            // Effets interactifs
            bg.fillProperty().bind(
                Bindings.when(hoverProperty())
                    .then(Color.GOLD)
                    .otherwise(Color.rgb(0, 0, 0, 0.5))
            );

            text.fillProperty().bind(
                Bindings.when(hoverProperty())
                    .then(Color.BLACK)
                    .otherwise(Color.WHITE)
            );

            // Animation au survol
            ScaleTransition st = new ScaleTransition(Duration.millis(200), this);
            st.setToX(1.1);
            st.setToY(1.1);
            
            this.setOnMouseEntered(e -> {
                st.playFromStart();
                setEffect(new DropShadow(10, Color.GOLD));
            });
            
            this.setOnMouseExited(e -> {
                st.stop();
                setScaleX(1);
                setScaleY(1);
                setEffect(null);
            });

            setOnMouseClicked(e -> action.run());

            getChildren().addAll(bg, text);
        }
    }
}

package com.ov.tetris.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PausePanel extends VBox {
    private Button menuButton;

    public PausePanel() {
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("pause");
        this.setSpacing(10);
        this.setVisible(false);

        Label paused = new Label("Paused");
        paused.setId("info-text");

        Label instruction = new Label("Press P to continue");
        instruction.setPadding(new Insets(0,0,20,0));
        instruction.setId("pause-instruction");

        menuButton = new Button("Back to menu");
        menuButton.setPadding(new Insets(5));

        this.getChildren().addAll(paused, instruction, menuButton);
    }

    public void setOnAction(Runnable action) { //sets button on action for controller
        menuButton.setOnAction(e -> action.run());
    }
}

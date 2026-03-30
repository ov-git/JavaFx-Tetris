package com.ov.tetris.view;

import com.ov.tetris.util.ResourceLoader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MenuPanel extends VBox {

    private final Button startButton;
    private final Button leaderboardButton;

    public MenuPanel() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(25);
        this.getStyleClass().add("menu");

        ImageView logo = ResourceLoader.getLogoImage();

        startButton = new Button("Start");
        startButton.setPadding(new Insets(5,30,5,30));
        leaderboardButton = new Button("Leaderboard");
        leaderboardButton.setPadding(new Insets(5,15,5,15));

        this.getChildren().addAll(logo, startButton, leaderboardButton);
    }

    public void setOnStart(Runnable action) { // sets button on action for controller
        startButton.setOnAction(e -> action.run());
    }
    public void setOnLeaderboard(Runnable action) { // sets button on action for controller
        leaderboardButton.setOnAction(e -> action.run());
    }

    
}

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
    private final Button quitButton;

    public MenuPanel() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.getStyleClass().add("menu");

        ImageView logo = ResourceLoader.getLogoImage();

        startButton = new Button("Start");
        leaderboardButton = new Button("Leaderboard");
        quitButton = new Button("Quit");

        this.getChildren().addAll(logo, startButton, leaderboardButton, quitButton);
    }

    public void setOnStart(Runnable action) { // sets button on action for controller
        startButton.setOnAction(e -> action.run());
    }
    public void setOnLeaderboard(Runnable action) { // sets button on action for controller
        leaderboardButton.setOnAction(e -> action.run());
    }
    public void setOnQuit(Runnable action) { // sets button on action for controller
        quitButton.setOnAction(e -> action.run());
    }

    
}

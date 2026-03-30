package com.ov.tetris.view;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import com.ov.tetris.model.Score;

public class ScoreList extends VBox {
    Label scoreLabel = new Label("Leaderboard: ");
    String scoreString;
    Button back;

    public ScoreList() {
        this.scoreString = "";

        this.setVisible(false);
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(5);
        this.getStyleClass().add("scores");
        
        back = new Button("Back");
    }
    
    
    public void initiateScores(ArrayList<Score> highScores) {
        this.getChildren().clear();

        this.getChildren().add(scoreLabel);
        for (Score score : highScores) {
            Text l = new Text(score.getPlayer() + ": " + score.getScore());
            this.getChildren().add(l);
        }
        this.getChildren().add(back);
    }

    public void setOnAction(Runnable action) { // sets button on action for controller
        back.setOnAction(e -> action.run());
    }
}

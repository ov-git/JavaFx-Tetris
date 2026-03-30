package com.ov.tetris.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import com.ov.tetris.model.GameModel;

public class InfoPanel extends VBox {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    private Label levelLabel;
    private Label linesLabel;
    private Label scoreLabel;

    public InfoPanel() {

        this.setPrefSize(WIDTH, HEIGHT);
        this.setMaxHeight(HEIGHT);
        this.getStyleClass().add("panel");
        this.setAlignment(Pos.CENTER);
        this.setSpacing(4);

        levelLabel = new Label();
        levelLabel.setId("level");
        linesLabel = new Label();
        linesLabel.setId("lines");
        scoreLabel = new Label();
        scoreLabel.setId("score");
        // Line separator = new Line();

        this.getChildren().addAll(levelLabel, linesLabel, scoreLabel);
    }

    public void render(GameModel model) {
        levelLabel.setText("Level: " + model.getLevel());
        linesLabel.setText("Lines: " + model.getLines());
        scoreLabel.setText("Score: " + model.getScore());

    }
}

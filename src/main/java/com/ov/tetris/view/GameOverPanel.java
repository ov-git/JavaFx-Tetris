package com.ov.tetris.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameOverPanel extends VBox {
    private Button menuButton;
    private ToggleButton saveButton;
    private Label scoreLabel;
    private Label over;
    private Text congratulation;
    private HBox textBox;
    private TextField nameField;

    public GameOverPanel() {
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("top-panel");
        this.setSpacing(15);
        this.setVisible(false);
        
        over = new Label("GAME OVER");
        over.setId("info-text");

        scoreLabel = new Label("SCORE: ");
        scoreLabel.setId("game-score");

        menuButton = new Button("Back to menu");
        menuButton.setPadding(new Insets(5));
        
        // Below fields shown only after highscore       
        nameField = new TextField();
        nameField.setPromptText("Write your name");
        nameField.setMaxWidth(300);
        
        saveButton = new ToggleButton("SAVE");
        saveButton.getStyleClass().add("toggle-button");

        congratulation = new Text("Congratulations! Your score made it to leaderboard!");
        congratulation.setId("congratulation");
        congratulation.setFill(Color.WHITE);
        congratulation.setVisible(false);

        textBox = new HBox();
        textBox.setAlignment(Pos.CENTER);
        textBox.setSpacing(5);
        textBox.setVisible(false);

        textBox.getChildren().addAll(nameField, saveButton);

        int maxLength = 10;

        nameField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                saveButton.setDisable(true);
            } else {
                saveButton.setDisable(false);
            }

            if (newText.length() > maxLength) {
                return null; // reject change to force max length of 10
            }

            if (!newText.matches("[a-zA-Z]*")) {
                return null; // reject non-letter characters
            }

            return change;
        }));

        this.getChildren().addAll(over, scoreLabel, congratulation, textBox, menuButton);
    }
    
    public void showGameOver(int score, boolean isHighScore, boolean isScoreLogged) {
        scoreLabel.setText("SCORE: " + score);

        if (isHighScore) {
            congratulation.setVisible(true);
            congratulation.setManaged(true);

            textBox.setVisible(true);
            textBox.setManaged(true);
        } else {
            congratulation.setVisible(false);
            congratulation.setManaged(false);

            textBox.setVisible(false);
            textBox.setManaged(false);
        }

        this.setVisible(true);
    }

    public void setScore(int score) {
        scoreLabel.setText("SCORE: " + Integer.toString(score));
    }

    public void setOnAction(Runnable action) { // sets button on action for controller
        menuButton.setOnAction(e -> action.run());
    }

    public void setOnLogScore(Runnable action) {
        saveButton.setOnAction(e -> {
            if (!nameField.getText().isEmpty()) {                
                action.run();
                // add style after click
                saveButton.setText("✔");
                saveButton.setDisable(true);
                textBox.setDisable(true);
            }
        });
    }

    public String getPlayerName() {
        return nameField.getText();
    }
}

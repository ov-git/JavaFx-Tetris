package com.ov.tetris;

import com.ov.tetris.controller.GameController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.ov.tetris.model.GameModel;
import com.ov.tetris.util.ResourceLoader;
import com.ov.tetris.view.BlockPanel;
import com.ov.tetris.view.GameOverPanel;
import com.ov.tetris.view.GamePanel;
import com.ov.tetris.view.InfoPanel;
import com.ov.tetris.view.MenuPanel;
import com.ov.tetris.view.PausePanel;
import com.ov.tetris.view.QueuePanel;
import com.ov.tetris.view.ScoreList;

public class Tetris extends Application {
    GamePanel gamePanel;
    GameController controller;
    GameModel model;   

    public void start(Stage stage) {
        model = new GameModel();
        gamePanel = new GamePanel();

        StackPane root = new StackPane();
        root.getStyleClass().add("root");

        HBox gameLayout = new HBox(); //During gameplay views

        VBox infoLayout = new VBox();
        VBox infoLayout2 = new VBox();
        infoLayout.setPadding(new Insets(0,20,0,0));
        infoLayout2.setPadding(new Insets(25, 0, 0 ,25));
        infoLayout.setAlignment(Pos.CENTER);
        
        InfoPanel infoPanel = new InfoPanel();
        QueuePanel queuePanel = new QueuePanel();
        BlockPanel hold = new BlockPanel(280 / 4);
        Label holdLabel = new Label("Hold: ");
        holdLabel.setId("label");

        ImageView logoImage = ResourceLoader.getLogoImage();
        logoImage.setFitWidth(110);
        logoImage.setFitHeight(55);
        
        infoLayout.getChildren().addAll(infoPanel, queuePanel);
        infoLayout2.getChildren().addAll(logoImage, holdLabel, hold);
        
        infoLayout.setSpacing(10);

        gameLayout.getChildren().addAll(infoLayout2, gamePanel, infoLayout);

        MenuPanel menu = new MenuPanel();
        PausePanel pause = new PausePanel();
        GameOverPanel gameOver = new GameOverPanel();
        ScoreList scoreList = new ScoreList();

        root.getChildren().addAll(gameLayout, menu, scoreList, pause, gameOver);
 
        Scene scene = new Scene(root, 500, 500);
        scene.getStylesheets().add("styles/css/styles.css");

        controller = new GameController(gamePanel,infoPanel,queuePanel, menu, scoreList, pause, gameOver,hold, model, scene);
        
        stage.setTitle("TETRIS");
        stage.setScene(scene);
        stage.show();
        
    }
    public static void main(String[] args) {       
        launch(args);
    }
}

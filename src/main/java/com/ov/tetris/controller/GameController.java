package com.ov.tetris.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import com.ov.tetris.model.GameModel;
import com.ov.tetris.util.GameFileHandler;
import com.ov.tetris.view.BlockPanel;
import com.ov.tetris.view.GameOverPanel;
import com.ov.tetris.view.GamePanel;
import com.ov.tetris.view.InfoPanel;
import com.ov.tetris.view.MenuPanel;
import com.ov.tetris.view.PausePanel;
import com.ov.tetris.view.QueuePanel;
import com.ov.tetris.view.ScoreList;

public class GameController {
    private GamePanel gamePanel;
    private InfoPanel infoPanel;
    private QueuePanel queuePanel;
    private MenuPanel menuPanel;
    private ScoreList scoreList;
    private PausePanel pausePanel;
    private GameOverPanel gameOverPanel;
    private BlockPanel holdPanel;
    private GameModel model;
    private Scene gameScene;
    private AnimationTimer gameLoop;

    private boolean downKeyPressed = false;
    private boolean leftKeyPressed = false;
    private boolean rightKeyPressed = false;

    public GameController(GamePanel gamePanel, InfoPanel infoPanel, QueuePanel queuePanel, MenuPanel menuPanel,
            ScoreList scoreList, PausePanel pausePanel, GameOverPanel gameOverPanel, BlockPanel holdPanel,
            GameModel model, Scene scene) {
        // Model
        this.model = model;
        // Views
        this.gamePanel = gamePanel;
        this.infoPanel = infoPanel;
        this.queuePanel = queuePanel;
        this.scoreList = scoreList;
        this.menuPanel = menuPanel;
        this.pausePanel = pausePanel;
        this.gameOverPanel = gameOverPanel;
        this.holdPanel = holdPanel;
        this.gameScene = scene;

        // actions
        menuPanel.setOnStart(() -> startGame());
        menuPanel.setOnLeaderboard(() -> showLeaderboard());
        menuPanel.setOnQuit(() -> quitGame());
        pausePanel.setOnAction(() -> openMenu());
        gameOverPanel.setOnAction(() -> openMenu());
        gameOverPanel.setOnLogScore(() -> logScore());
        scoreList.setOnAction(() -> showLeaderboard());

        String scoreString = GameFileHandler.readGameScoreFile();
        model.parseScoreList(scoreString);
        model.orderHighScores();
        
    }
    
    private void setupKeyListeners()  {
        gameScene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();
            switch (code) {
                case SHIFT:
                    model.holdPiece();
                    break;
                case P:
                    pauseGame();
                    break;                             
                case LEFT:
                case A:
                    leftKeyPressed = true;
                    break;
                case RIGHT: 
                case D: 
                    rightKeyPressed = true;
                    break;
                case DOWN: 
                case S: 
                    downKeyPressed = true;
                    break;
                case UP:
                case W:
                    model.rotate();
                    break; // single press action
                default:
                    break;
        }
        });
        gameScene.setOnKeyReleased(e -> {
            KeyCode code = e.getCode();
            switch (code) {
                case LEFT:
                case A:
                    leftKeyPressed = false;
                    break;
                case RIGHT:
                case D:
                    rightKeyPressed = false;
                    break;
                case DOWN:
                case S:
                    downKeyPressed = false;
                    break;
                default:
                    break;
            }
        });
    }

    public void startGame() {
        //Initiate keylisteners
        setupKeyListeners();

        // Hide menu
        menuPanel.setVisible(false);

        // Start the game loop
        gameLoop = new AnimationTimer() {

            private long lastUpdate = 0;
            private final long INTERVAL = 1_000_000_000 / 60;

            @Override
            public void handle(long now) {
                // Update game state and render
                if (now - lastUpdate >= INTERVAL) {
                    update();
                    lastUpdate = now;
                }
                render();
            }
        };
        gameLoop.start();
        model.setGameState(2); //PLAYING
    }

    public void gameOver() {
        //Stop game and log the score
        int score = model.getScore();
        gameLoop.stop();

        //Show game over screen
        boolean isHighScore = model.checkHighScore();
        gameOverPanel.showGameOver(score, isHighScore, model.getIsScoreLogged());
    }
    
    public void logScore() {
        if (!model.getIsScoreLogged()) {
            String name = gameOverPanel.getPlayerName();
            model.logHighScore(name);
            model.toggleIsScoreLogged();
            model.logHighScores();       
        }
    }

    public void pauseGame() {
        int gameState = model.getGameState();

        if (gameState == 2) {//Playing
            model.setGameState(3);//PAUSED
            gameLoop.stop();
            pausePanel.setVisible(true);
        } else {
            model.setGameState(2);//PLAYING
            gameLoop.start();
            pausePanel.setVisible(false);
        }
    }
    
    public void openMenu() {
        model.startNewGame(); // Resets the game state
        model.setGameState(1);//STOPPED

        pausePanel.setVisible(false);
        gameOverPanel.setVisible(false);

        menuPanel.setVisible(true);
    }

    private void update() {
        if (model.getGameState() == 1) {
            gameOver();
        }
        if (leftKeyPressed) {
            model.moveLeft();
        }
        if (rightKeyPressed) {
            model.moveRight();
        }
        if (downKeyPressed) {
            model.softDrop();
        }
        model.update();
    }
    
    public void showLeaderboard() {
        if (scoreList.isVisible()) {
            menuPanel.setVisible(true);
            scoreList.setVisible(false);            
        } else {
            scoreList.initiateScores(model.getHighScores());
            scoreList.setVisible(true);
            menuPanel.setVisible(false);
        }
    }

    private void render() {
        // draw model state
        gamePanel.render(model);
        infoPanel.render(model);
        queuePanel.render(model.getPieceQue());
        holdPanel.render(model.getHoldPiece(), model.getCanHold());
    }

    private void quitGame() {
        System.exit(0);
    }
}

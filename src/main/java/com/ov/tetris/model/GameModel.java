package com.ov.tetris.model;

import java.util.ArrayList;
import java.util.Random;

import com.ov.tetris.util.GameFileHandler;

public class GameModel {

    public static final int ROWS = 20;
    public static final int COLS = 10;
    
    public static final int LEVELUPAFTERLINES = 20; //lines for level increase;   

    // private enum GameState { MENU, PLAYING, PAUSED };
    private int gameState; //{ MENU, PLAYING, PAUSED }
    private int[][] grid; //Game board
    private Piece currentPiece; // Currently active piece
    private Piece holdPiece; 
    private Piece[] pieceQue;

    private boolean canHold = true;
    private boolean isScoreLogged = false;

    private ArrayList<Score> highScores;
    private int highScore = 0;
    
    private int[] dropSpeeds = {
            0, //Level:
            45, // 1
            35, // 2
            25, // 3
            20, // 4
            15, // 5
            12, // 6
            10, // 7
            8, // 8
            6, // 9
            5, // 10
            4, // 11
            4, // 12
            3, // 13
            3, // 14
            2, // 15
            2, // 16
            1, // 17
            1, // 18
            1, // 19
            0 // 20+
    };
    private int dropInterval = 45; //Initial drop speed, changes with level
    private int autoDropCounter = 0;
    private boolean softDrop = false;
    
    private int moveInterval = 5; //Slows side moving to maintain control
    private int moveTimer = 0;

    //Slides
    private int SLIDETIME = 10;
    private int slideTimer = 0;
    private boolean onGround = false;
    private int resetCount = 0;
    private int maxResets = 15;

    private int level = 1;
    private int lines = 0; // Cleared lines
    private int score = 0;

    public GameModel() {
        this.highScores = new ArrayList<Score>();  
        startNewGame();
    }

    public void startNewGame() {
        // start piece at top
        grid = new int[ROWS][COLS];
        currentPiece = null;
        score = 0;
        level = 1;
        lines = 0;
        isScoreLogged = false;
    }

    public void endGame() {
        setGameState(1);       
    }

    public void update() {
        if (currentPiece == null) {
            initiatePieces();
            generateNewPiece();
        }

        autoDropCounter++;
        moveTimer++;

        boolean canMoveDown = canMove(0, 1);

        if (autoDropCounter >= dropInterval || softDrop) {
            autoDropCounter = 0;

            if (canMoveDown) {
                moveDown();
                onGround = false;
                slideTimer = 0;
            } else {
                onGround = true;
            }
        }
        if (onGround) {
            slideTimer++;

            if (slideTimer >= SLIDETIME) {
                lockPiece();
                setSpeedByLevel();
                clearRow();
                nextPiece();

                resetCount = 0;
                slideTimer = 0;
                onGround = false;
            }
        }
    }
    
    public void resetSlidetime() {
        if (onGround && resetCount < maxResets) {
            slideTimer = 0;
            resetCount++;
        }
    }

    public void moveLeft() {
        if (moveTimer >= moveInterval) {
            if (canMove(-1, 0)) {
                currentPiece.moveLeft();
                moveTimer = 0;

                resetSlidetime();
            }
        }
    }
    
    public void moveRight() {
        if (moveTimer >= moveInterval) {
            if (canMove(1, 0)) {
                currentPiece.moveRight();
                moveTimer = 0;

                resetSlidetime();
            }
        }
    }

    public void rotate() {
        // Create a temporary piece and see if it can rotate
        currentPiece.computeRotation();
        Block[] tempBlocks = currentPiece.getTempBlocks();
        if (canRotate(tempBlocks)) {
            // If rotation is successful apply it to the actual piece
            currentPiece.applyRotation();
            resetSlidetime();
        } else {
            tryKick(tempBlocks);
        }
    }

    public void holdPiece() {
        if (canHold()) {
            if (holdPiece == null) {
                holdPiece = generatePieceByType(currentPiece.getType());
                nextPiece();
            } else {
                Piece temp = holdPiece;
                holdPiece = generatePieceByType(currentPiece.getType());
                currentPiece = temp;
            }            
        }
    }
    
    public boolean canHold() {
        if (canHold) {
            canHold = false;
            return true;
        }
        return canHold;
    }
    
    public void softDrop() {//Key down
        softDrop = true;
        score += 1; //Scores are rewarded for holding down      
    }

    public void moveDown() {
        softDrop = false;       
        for (Block b : currentPiece.getBlocks()) {
            b.y++;
        }                                            
    }

    private boolean canMove(int dx, int dy) {
        // Check bounds
        for (Block b : currentPiece.getBlocks()) {
            int newX = b.x + dx;
            int newY = b.y + dy;

            // Down move
            if (newY >= ROWS)
                return false;
            // Side move
            if (newX < 0 || newX >= COLS)
                return false;

            // Check collision with placed blocks
            if (newY >= 0) {
                int newPosition = grid[newY][newX];
                if (newY >= 0 && newPosition != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void tryKick(Block[] blocks) {
        int[] kicks = { -1, -1, 3 }; // sequence of x adjustments

        for (int kick : kicks) {
            for (Block b : blocks) {
                b.x += kick;
            }
            if (canRotate(blocks)) {
                currentPiece.applyRotation();
                return;
            }
        }
    }
    
    public boolean canRotate(Block[] blocks) {
        for (Block b : blocks) {
            int x = b.x;
            int y = b.y;           
            // Left wall collision
            if (x < 0 || y >= ROWS) {
                return false;
            }
            // Right wall collision
            if (x >= COLS || y >= ROWS) {
                return false;               
            }
            // Check collision with placed blocks
            if (y >= 0 && grid[y][x] > 0) {
                return false;
            }
        }
        return true;
    }
        
    private void lockPiece() {
        // Locks active piece to the grid
        for (Block block : currentPiece.getBlocks()) {
            if (block.y >= 0) {
                grid[block.y][block.x] = block.type;
            }
        }
        canHold = true;
    }
    
    public void clearRow() {
        int cleared = 0; // Counts cleared lines for placed piece
        for (int i = ROWS - 1; i > 0; i--) {
            boolean full = true;

            for (int j = 0; j < COLS; j++) {
                grid[i + cleared][j] = grid[i][j];
                if (grid[i][j] == 0) {
                    full = false;
                }
            }
            if (full) {
                cleared++;
                for (int j = 0; j < COLS; j++) {
                    grid[i][j] = 0;
                }
                full = false;
            }
        }
        lines += cleared;
        addScore(cleared); // add score for cleared lines
        checkLevelUp();
    }

    public void addScore(int lines) {
        //Following NES scoring system;
        int plusScore = 0;
        if (lines == 1) {
            plusScore = 40 * level;
        } else if (lines == 2) {
            plusScore = 100 * level;
        } else if (lines == 3) {
            plusScore = 300 * level;
        } else if (lines == 4) {
            plusScore = 1200 * level;
        }
        score += plusScore;
    }
    
    public void checkLevelUp() {
        int levelMark = level * LEVELUPAFTERLINES;
        if (lines >= levelMark) {
            level++;
            setSpeedByLevel();
        }
    }

    public void setSpeedByLevel() {
        int newSpeed = level < dropSpeeds.length ? dropSpeeds[level] : 0;
        dropInterval = newSpeed;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getGameState()  {
        return gameState;
    }

    public int getLevel() {
        return level;
    }
    
    public int getLines() {
        return lines;
    }
    
    public int getScore() {
        return score;
    }
    
    public boolean getCanHold() {
        return canHold;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public Piece getHoldPiece() {
        return holdPiece;
    }

    public Piece[] getPieceQue() {
        return pieceQue;
    }

    public boolean getIsScoreLogged() {
        return isScoreLogged;

    }

    public void toggleIsScoreLogged() {
        isScoreLogged = !isScoreLogged;
    }

    public int getDropInterval() {
        return dropInterval;
    }

    public ArrayList<Score> getHighScores() {
        return highScores;
    }

    public void setGameState(int state) {
        this.gameState = state;
    }

    public Piece generatePieceByType(int type) {
        int pieceType;
        if (type == 0) {
            Random random = new Random();
            pieceType =  random.nextInt(7);            
        } else {
            pieceType = type -1;
        }
        Piece newPiece;
        switch (pieceType) {
            case 0:
                newPiece = new PieceSq();
                break;
            case 1:
                newPiece = new PieceL1();
                break;
            case 2:
                newPiece = new PieceL2();
                break;
            case 3:
                newPiece = new PieceT();
                break;
            case 4:
                newPiece = new PieceI();
                break;
            case 5:
                newPiece = new PieceZ1();
                break;
            case 6:
                newPiece = new PieceZ2();
                break;
            default:
                newPiece = new PieceSq();
        }
        return newPiece;       
    }

    public Piece generateNewPiece() {
        return generatePieceByType(0);
    }
    
    public void initiatePieces() {
        pieceQue = new Piece[4];
        currentPiece = generateNewPiece();

        for (int i = 0; i < pieceQue.length; i++) {
            Piece newPiece = generateNewPiece();
            pieceQue[i] = newPiece;
        }
    }
    
    public void nextPiece() {
        currentPiece = pieceQue[0];
        for (int i = 0; i < pieceQue.length - 1; i++) {
            pieceQue[i] = pieceQue[i + 1];
        }
        pieceQue[3] = generateNewPiece();

        if (!canMove(0, 1)) {
            endGame();
        }
    }

    public void parseScoreList(String scoreString) {
        // Parses score string read from file to highscore list
        String[] splitted = scoreString.split(",");
        for (String s : splitted) {
            try {
                String trimmed = s.trim();
                String parsedName = trimmed.split(" ")[0];
                int parsedScore = Integer.parseInt(trimmed.split(" ")[1]);

                Score ss = new Score(parsedScore, parsedName);
                highScores.add(ss);
            } catch (Exception e) {
                System.out.println("Error loading scores");
            }
        }
    }
    
    public void orderHighScores() {
        // Uses arraylist sort method to set scores in order:
        if (highScores.size() > 0) {
            highScores.sort((a, b) -> {
                return (a.getScore() > b.getScore()) ? -1 : +1;
            });
            //highScore = highScores.getFirst().getScore(); //Might not work depending on versions
            highScore = highScores.get(0).getScore();
        }
    }
    
    public boolean checkHighScore() {
        // Checks if score is highscore and should be logged
        if (highScores.size() < 5)
            return true;
        for (Score s : highScores) {
            if (score > s.getScore()) {
                return true;
            }
        }
        return false;
    }

    public void logHighScore(String name) {
        Score gameScore = new Score(score, name);
        highScores.add(gameScore);
        orderHighScores();

        if (highScores.size() > 5) {
            //highScores.removeLast(); //Might not work depending on versions
            highScores.remove(highScores.size() - 1);
        }
    }
    
    public void logHighScores() {
        String scoreString = "";
        for (Score s : highScores) {
            if(!s.getPlayer().isEmpty()) {
                scoreString += s.getPlayer() + " " + s.getScore() + ",";               
            }
        }
        GameFileHandler.writeGameScoreFile(scoreString);
    }
}

package com.ov.tetris.view;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import com.ov.tetris.model.Block;
import com.ov.tetris.model.GameModel;

public class GamePanel extends StackPane {

    public static final int WIDTH = 250;
    public static final int HEIGHT = 500;

    private Canvas canvas;
    private GraphicsContext gc;
    
    public GamePanel() {
        this.setPrefSize(250, 400);
        this.getStyleClass().add("game-panel");

        canvas = new Canvas(180, 360);
        gc = canvas.getGraphicsContext2D();

        this.getChildren().add(canvas);
    }
    
    public void render(GameModel model) {

        gc.clearRect(0, 0, 180, 360);
        // gc.setStroke(Color.WHITE);
        

        // Draw game grid and old pieces
        int blockSize = 18;
        int[][] grid = model.getGrid();
        for (int c = 0; c < GameModel.COLS; c++) {
            for (int r = 0; r < GameModel.ROWS; r++) {
                int currentBlock = grid[r][c];
                gc.setFill(BlockColor.getBlockColor(currentBlock));
                gc.fillRect(c * blockSize, r * blockSize, blockSize - 1, blockSize - 1);              
            }
        }
        // Draw current piece
        Block[] piece = model.getCurrentPiece().getBlocks();
        for (Block block : piece) {
            gc.setFill(BlockColor.getBlockColor(block.type));
            gc.fillRect(block.x * blockSize, block.y * blockSize, blockSize - 1, blockSize - 1);
        }
        gc.setStroke(Color.WHITE);
        gc.strokeRect(0, 0, 180, 360);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGC() {
        return canvas.getGraphicsContext2D();
    }
}

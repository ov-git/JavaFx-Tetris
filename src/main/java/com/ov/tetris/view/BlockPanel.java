package com.ov.tetris.view;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import com.ov.tetris.model.Block;
import com.ov.tetris.model.Piece;

public class BlockPanel extends StackPane {

    private int size;

    private Canvas blockCanvas;
    private GraphicsContext gc;

    public BlockPanel(int size) {
        this.size = size;
        this.setPrefSize(size, size);
        this.setMaxHeight(size);
        this.getStyleClass().add("panel");

        blockCanvas = new Canvas(size, size);
        gc = blockCanvas.getGraphicsContext2D();

        this.setAlignment(Pos.CENTER);
        this.getChildren().add(blockCanvas);
    }

    public void render(Piece piece, boolean showColor) {

        if (piece != null) {

            Color blockColor = showColor ? BlockColor.getBlockColor(piece.getType()) : Color.SLATEGRAY;
            gc.clearRect(0, 0, size, size);
    
            int blockSize = 16;
            int offset = size / 3;
            int offsetY = size / 2;
    
            for (Block b : piece.getBlocks()) {
                gc.setFill(blockColor);   
                gc.fillRect(
                    (b.x - piece.getX()) * blockSize + offset,
                    (b.y - piece.getY()) * blockSize + offsetY,
                    blockSize - 1,
                    blockSize - 1
                );
            }          
        }

    }
    
}

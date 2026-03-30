package com.ov.tetris.model;

public class PieceI extends Piece {

    public PieceI() {
        type = 5;
        int x = 4;
        int y = 0;
        int[] xs = { x, x - 1, x + 1, x + 2 };
        int[] ys = { y, y, y, y };

        blocks = generateBlocks(xs, ys, type);
    }

    @Override
    void updateShape(int x, int y, int position) {

        if (position == 1 || position == 3) {
            int[] xs = { x, x - 1, x + 1, x + 2 };
            int[] ys = { y, y, y, y };
            tempBlocks = generateBlocks(xs, ys, type);
            
        } else {
            int[] xs = { x, x, x, x };
            int[] ys = { y, y - 1, y + 1, y + 2 };
            tempBlocks = generateBlocks(xs, ys, type);
        }
    }
}
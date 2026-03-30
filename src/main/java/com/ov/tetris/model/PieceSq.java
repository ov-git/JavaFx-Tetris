package com.ov.tetris.model;

public class PieceSq extends Piece {
    public PieceSq() {
        type = 1;
        int x = 4;
        int y = 0;
        int[] xs = { x, x + 1, x, x + 1 };
        int[] ys = { y, y, y - 1, y - 1 };
        blocks = generateBlocks(xs, ys, type);
    }
    @Override
    void updateShape(int x, int y, int position) {
        int[] xs = { x, x + 1, x, x + 1 };
        int[] ys = { y, y, y - 1, y - 1 };
        tempBlocks = generateBlocks(xs, ys, type);       
    }
}

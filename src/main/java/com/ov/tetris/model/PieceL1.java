package com.ov.tetris.model;

public class PieceL1 extends Piece {

    public PieceL1() {
        type = 2;
        int x = 4;
        int y = 0;
        int[] xs = { x, x - 1, x + 1, x + 1 };
        int[] ys = { y, y, y, y - 1 };
        blocks = generateBlocks(xs, ys, type);
    }

    @Override
    void updateShape(int x, int y, int position) {
        if (position == 1) {
            int[] xs = { x, x - 1, x + 1, x + 1 };
            int[] ys = { y, y, y, y - 1 };
            tempBlocks = generateBlocks(xs, ys, type);
        } else if (position == 2) {
            int[] xs = { x, x, x, x + 1 };
            int[] ys = { y, y - 1, y + 1, y + 1 };     
            tempBlocks = generateBlocks(xs, ys, type);
        } else if (position == 3) {
            int[] xs = { x, x + 1, x - 1, x - 1 };
            int[] ys = { y, y, y, y + 1 };
            tempBlocks = generateBlocks(xs, ys, type);         
        } else {
            int[] xs = { x, x , x , x - 1 };
            int[] ys = { y, y + 1, y - 1, y -1 };
            tempBlocks = generateBlocks(xs, ys, type);
        }
    }
    
}

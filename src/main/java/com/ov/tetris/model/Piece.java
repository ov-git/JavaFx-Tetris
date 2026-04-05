package com.ov.tetris.model;

public abstract class Piece {
    protected Block[] blocks = new Block[4];
    protected Block[] tempBlocks = new Block[4];
    protected int type;
    protected int position = 1;

    public int getType() {
        return type;
    }
    
    public Block[] getBlocks() {
        return blocks;
    }

    public Block[] getTempBlocks() {
        return tempBlocks;
    }

    public void moveLeft() {
        for (Block b : blocks) {
            b.x -= 1;
        }
    }

    public void moveRight() {
        for (Block b : blocks) {
            b.x += 1;
        }
    }

    public int getY() {
        Block baseBlock = blocks[0];
        return baseBlock.y;
    }

    public int getX() {
        Block baseBlock = blocks[0];
        return baseBlock.x;
    }
    
    public void rotate(int x, int y) {
        if (position < 4) {
            position++;
        } else {
            position = 1;
        }
        updateShape(x,y, position);
    };

    public void computeRotation() {
        int nextPosition = (position % 4) + 1;
        updateShape(getX(), getY(), nextPosition);
    }

    public void applyRotation() {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].x = tempBlocks[i].x;
            blocks[i].y = tempBlocks[i].y;
        }
        position = (position % 4) + 1;
    }

    protected Block[] generateBlocks(int[] xlist, int[] ylist, int type) {

        Block[] newBlocks = new Block[4];

        for (int i = 0; i < xlist.length; i++) {
            newBlocks[i] = new Block(xlist[i], ylist[i], type);
        }

        return newBlocks;
    }
    
    abstract void updateShape(int currentX, int currentY, int position);
}
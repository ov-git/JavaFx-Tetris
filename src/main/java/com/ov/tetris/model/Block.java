package com.ov.tetris.model;

public class Block {
    public int x;
    public int y;
    public int type; //To determine color for rendering

    public Block(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }   
}

package com.ov.tetris.model;

public class Score {
    private int score;
    private String player;

    public Score(int score, String player) {
        this.score = score;
        this.player = player;
    }
    
    public int getScore() {
        return score;
    }
    public String getPlayer() {
        return player;
    }
}

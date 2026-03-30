package com.ov.tetris.view;

import javafx.scene.paint.Color;

public class BlockColor {
// Utility class for view components to get correct color by block type
    public static Color getBlockColor(int type)  {  
        switch (type) {
            case 1: return Color.YELLOW; // Square
            case 2: return Color.ORANGE; // L1
            case 3: return Color.BLUE; // L2
            case 4: return Color.HOTPINK; // T
            case 5: return Color.CYAN; // I
            case 6: return Color.RED; // Z1
            case 7: return Color.GREEN; // Z2
            default: return Color.BLACK;
        }
    }  
}

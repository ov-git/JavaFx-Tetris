package com.ov.tetris.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResourceLoader {

    public static ImageView getLogoImage() {
       try {
        Image logo = new Image("tetris.png");
        ImageView iv = new ImageView(logo);
        return iv;
        } catch (Exception e) {
            System.err.println("Error loading resources:" + e);
            return new ImageView();
        }
    }
    
}

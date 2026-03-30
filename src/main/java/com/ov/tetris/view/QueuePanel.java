package com.ov.tetris.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import com.ov.tetris.model.Piece;

public class QueuePanel extends VBox {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 280;
    
    private BlockPanel s1;
    private BlockPanel s2;
    private BlockPanel s3;
    private BlockPanel s4;

    public QueuePanel() {
        Label next = new Label("Next: ");
        next.setId("label");
        s1 = new BlockPanel(HEIGHT/4);
        s2 = new BlockPanel(HEIGHT/4);
        s3 = new BlockPanel(HEIGHT/4);
        s4 = new BlockPanel(HEIGHT / 4);

        VBox blocks = new VBox();
        blocks.setSpacing(2);
        blocks.getChildren().addAll(s1, s2, s3, s4);
        this.getChildren().addAll(next, blocks);
    }

    public void render(Piece[] pieces) {
    
    s1.render(pieces[0], true);
    s2.render(pieces[1], true);
    s3.render(pieces[2] , true);
    s4.render(pieces[3] , true);
    }
}

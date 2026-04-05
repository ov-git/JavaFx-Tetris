module tetris.app {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.ov.tetris;
    opens com.ov.tetris to javafx.fxml;
}
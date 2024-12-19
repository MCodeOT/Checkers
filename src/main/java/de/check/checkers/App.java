package de.check.checkers;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

import de.check.checkers.ui.GraphicalUI;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        GraphicalUI defScene = new GraphicalUI(root);



        stage.setScene(GraphicalUI.checkerBoard());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
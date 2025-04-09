package de.check.checkers;

import de.check.checkers.ui.GraphicalUI;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(GraphicalUI.getInstance());
        stage.show();
    }
}
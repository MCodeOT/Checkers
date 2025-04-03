package de.check.checkers;

import de.check.checkers.ui.GraphicalUI;
import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
        //Board board1= new Board(8);
        //System.out.println(board1);
    }

    @Override
    public void start(Stage stage) throws IOException {
        StackPane root = new StackPane();
        GraphicalUI graphicalUI = new GraphicalUI(root);
        stage.setScene(graphicalUI);
        stage.show();
    }
}
package de.check.checkers;


import de.check.checkers.structures.Board;
import de.check.checkers.structures.Position;
import de.check.checkers.utils.Controller;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

import de.check.checkers.ui.GraphicalUI;

import netscape.javascript.JSObject;

public class App extends Application {

    private GraphicalUI graphicalUI;


    @Override
    public void start(Stage stage) throws IOException {
        StackPane root = new StackPane();
        graphicalUI = new GraphicalUI(root);
        stage.setScene(graphicalUI);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();





//        Controller c = new Controller();
//        System.out.println(c.getBoardSize());

//        Position currentPos = new Position(0, 2);
//        Position targetPos = new Position(1, 3);

//        c.movePiece(currentPos, targetPos);
//        System.out.println(c.getBoard());


    }


}
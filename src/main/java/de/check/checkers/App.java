package de.check.checkers;


import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

import de.check.checkers.ui.GraphicalUI;

import netscape.javascript.JSObject;

public class App extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        stage.setScene(GraphicalUI.checkerBoard());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}
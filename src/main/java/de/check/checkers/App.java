package de.check.checkers;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        WebView webView = new WebView();
        System.out.println("Hello World");
        webView.getEngine().load("http://google.com");

        VBox vBox = new VBox(webView);
        Scene scene = new Scene(vBox, 960, 600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
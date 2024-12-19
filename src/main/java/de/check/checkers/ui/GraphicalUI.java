package de.check.checkers.ui;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class GraphicalUI extends Scene {
    public GraphicalUI(Parent root){
        super(root);

    }

    public static Scene checkerBoard()  {
        // WebView-Element erstellen
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();

        // Resource laden
        URL url = GraphicalUI.class.getResource("/Checkers.html");
        if (url == null) {
            return null;
        }

        // Datei in WebView laden
        webEngine.load(url.toString());

        // WebView in einer VBox einbetten
        VBox vbox = new VBox(browser);

        // Scene erstellen und zurückgeben
        return new Scene(vbox);
    }



}

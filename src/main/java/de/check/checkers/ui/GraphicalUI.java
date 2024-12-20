package de.check.checkers.ui;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import de.check.checkers.App;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;


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
        webEngine.load(url.toExternalForm());

        // Java-Callback für JavaScript einrichten
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                // Verbindung zwischen Java und JavaScript erstellen
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("java", new JavaFXInterface());
            }
        });

        // WebView in einer VBox einbetten
        VBox vbox = new VBox(browser);

        // Scene erstellen und zurückgeben
        return new Scene(vbox);
    }
    // JavaScript-Interface für JavaScript-Callbacks
    public static class JavaFXInterface {
        public void handleFieldClick(String color) {
            // Logik für angeklicktes Feld
            System.out.println("Das angeklickte Feld ist: " + color);
        }
    }

}

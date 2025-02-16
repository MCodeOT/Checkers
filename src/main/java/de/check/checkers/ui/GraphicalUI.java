package de.check.checkers.ui;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import de.check.checkers.App;
import de.check.checkers.structures.Position;
import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;


public class GraphicalUI extends Scene {

    WebEngine webEngine;
    public GraphicalUI(Parent root){
        super(root);
        checkerBoard();
    }

    private void checkerBoard()  {
        // WebView-Element erstellen
        WebView browser = new WebView();
        webEngine = browser.getEngine();

        // Resource laden
        URL url = GraphicalUI.class.getResource("/Checkers.html");
        if (url == null) {
            return;
        }

        // Datei in WebView laden
        webEngine.load(url.toExternalForm());



        // Java-Callback für JavaScript einrichten
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // Verbindung zwischen Java und JavaScript erstellen
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("java", new JavaFXInterface());
            }
        });

        // WebView in einer VBox einbetten
        VBox vbox = new VBox(browser);

        // Scene erstellen und zurückgeben
        ((StackPane) getRoot()).getChildren().add(vbox);
    }
    // JavaScript-Interface für JavaScript-Callbacks
    public static class JavaFXInterface {
        public void handleFieldClick(String data) {
            // Logik für angeklicktes Feld
            String[] parts = data.split(", ");
            int x = 0;
            int y = 0;
            if (parts.length == 3) {
                String color = parts[0];
                x = Integer.parseInt(parts[1]);
                y = Integer.parseInt(parts[2]);


                // Logik für die Verarbeitung
                System.out.print("Feldfarbe: " + color);
                System.out.print(" | X-Koordinate: " + x);
                System.out.println(" | Y-Koordinate: " + y);
            } else {
                System.out.println("Ungültige Daten empfangen: " + data);
            }
            Position clickedPos = new Position(x, y);
            Position targetPos = new Position(7, 1);

            // Vergleich von Koordinaten vom angeklickten Feld und Testkoordinaten
            if (targetPos.equals(clickedPos)) {
                System.out.println("Positionen stimmen überein!");
            } else {
                System.out.println("Position stimmen nicht überein!");
            }



        }

        public void handleReloadCheckers()   {

        }

    }


}

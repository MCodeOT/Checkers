package de.check.checkers.ui;

import de.check.checkers.structures.Position;
import de.check.checkers.utils.Controller;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.net.URL;

/**
 * <b>Class for </b>
 */
public class GraphicalUI extends Scene {

    private WebEngine webEngine;
    private JSObject window;
    private Controller controller;

    public GraphicalUI(Parent root) {
        super(root);
        checkerBoard();
    }

    /**
     * <b>Creates webview, loads 'Checkers.html' and adds a new member to the {@link JSObject}</b>
     *
     * @see "Checkers.html"
     */
    private void checkerBoard() {
        WebView browser = new WebView();
        webEngine = browser.getEngine();

        URL url = GraphicalUI.class.getResource("/Checkers.html");
        if (url == null) {
            return;
        }

        webEngine.load(url.toExternalForm());
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                controller = new Controller(8);
                window = (JSObject) webEngine.executeScript("window");
                window.setMember("java", new JavaFXInterface());
            }
        });
        VBox vbox = new VBox(browser);
        ((StackPane) getRoot()).getChildren().add(vbox);
    }

    /**
     * <b>Handles general communication between JS and Java</b>
     *
     * @see "scripts.js"
     */
    public class JavaFXInterface {

        /**
         * <b>Calls the controller and checks, if there is a piece at the given coordinates</b>
         *
         * @param x coordinate
         * @param y coordinate
         * @return true, if there is a piece at the position
         */
        public boolean isPieceAtPosition(int x, int y) {
            return controller.getPieceFromPosition(new Position(x, y)) != null;
        }

        /**
         * <b>Calls the controller and checks, if the piece at the given coordinates is black</b>
         *
         * @param x coordinate
         * @param y coordinate
         * @return true, if the piece is black
         */
        public boolean isPieceBlack(int x, int y) {
            return controller.isPieceBlack(controller.getPieceFromPosition(new Position(x, y)));
        }

        public void getActionFromController(int current1DPos) {
            Position currentPos = convertToPosition(current1DPos);
            int actionCode = controller.handlePositionTransmission(currentPos);

            switch (actionCode) {
                case 0:
                    break;
                case 1:
                    System.out.println("Case 1: " + actionCode);
                    callJavaScript("removeHighlightFromClickedPiece", current1DPos);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    break;
                case 30:
                    System.out.println("Case 30: " + actionCode);
                    callJavaScript("addHighlightToClickedPiece", current1DPos);
                    break;
                case 100:
                    break;
                default:
            }
        }

        public void copyBoard() {

        }

        /**
         * <b>Converts the given coords into a one-dimensional number by multiplying the y coordinate with the boardsize and adding the x coordinate. </b>
         *
         * @param x coordinate
         * @param y coordinate
         * @return a one dimensional coord-index
         */
        public int convertToOneDimensional(int x, int y) {
            return y * controller.getBoardSize() + x;
        }

        /**
         * <b>Takes the integer i and converts it back to x and y coordinates with 2 different equasions</b>
         *
         * @param i 1 dimensional Position received from JS
         * @return a {@link Position}
         */
        public Position convertToPosition(int i) {
            // 1 to 2D: x=i%fieldsize       y=i/fieldsize
            int x = i % controller.getBoardSize();
            int y = i / controller.getBoardSize();
            return new Position(x, y);
        }
        /**
         * <b>Calls the controller to fetch the boardsize</b>
         *
         * @return the size of the checkerboard
         */
        public int getBoardSize() {
            return controller.getBoardSize();
        }

        public void printShit(String shit) {
            System.out.println(shit);
        }

        private void callJavaScript(String function, Object... parameter) {
            Platform.runLater(() -> {
                window.call(function, parameter);
            });
        }
    }
}

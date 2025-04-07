package de.check.checkers.ui;

import de.check.checkers.structures.Board;
import de.check.checkers.utils.PTC;
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
    private static final GraphicalUI instance = new GraphicalUI(new StackPane());

    private WebEngine webEngine;
    private JSObject window;
    private Controller controller;

    private GraphicalUI(Parent root) {
        super(root);
        checkerBoard();
    }
    public static GraphicalUI getInstance() {
        return instance;
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
                controller = Controller.getInstance();
                controller.setBoardSize(8);
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

        /**
         * <b>Takes the currently clicked position, converts it to a 2 dimensional and decides, depending on the returned code form the controller, what JS-Method should be called</b>
         *
         * @param current1DPos current 1 dimensional position of the clicked piece
         */
        public void getActionFromController(int current1DPos) {
            Position currentPos = convertToPosition(current1DPos);
            System.out.println("Java-Position: " + currentPos.getX() + " " + currentPos.getY());
            PTC actionMessage = controller.handlePositionTransmission(currentPos);
            switch (actionMessage) {
                case PTC.FAILURE_GENERIC_UNAVAILABLE_MOVE:
                    break;
                case PTC.FAILURE_BOTH_POSITIONS_IDENTICAL:
                    System.out.println(PTC.FAILURE_GENERIC_UNAVAILABLE_MOVE.ordinal() + ": " + actionMessage);
                    callJavaScript("removeHighlightFromClickedPiece");
                    break;
                case PTC.FAILURE_NO_PIECE_ON_FIELD:
                    System.out.println(PTC.FAILURE_NO_PIECE_ON_FIELD.ordinal() + ": " + actionMessage);
                    callJavaScript("errorHighlight");
                    break;
                case PTC.FAILURE_WRONG_PLAYER_SELECTED:
                    System.out.println(PTC.FAILURE_WRONG_PLAYER_SELECTED.ordinal() + ": " + actionMessage);
                    callJavaScript("errorHighlight");
                    break;
                case PTC.SUCCESSFUL_GENERIC_AVAILABLE_MOVE:
                    System.out.println(PTC.SUCCESSFUL_GENERIC_AVAILABLE_MOVE.ordinal() + ": " + actionMessage);
                    callJavaScript("copyBoard");
                    callJavaScript("removeHighlightFromClickedPiece");
                    break;
                case PTC.SUCCESSFUL_FIRST_CLICK:
                    System.out.println(PTC.SUCCESSFUL_FIRST_CLICK.ordinal() + ": " + actionMessage);
                    callJavaScript("addHighlightToClickedPiece");
                    break;
                default:
            }
        }

        public void copyBoard() {
            Board board = controller.getBoard();
            for (int y = 0; y < getBoardSize(); y++) {
                for (int x = 0; x < getBoardSize(); x++) {
                    if (board.getPieceFromPosition(new Position(x, y)) != null) {
                        if (isPieceBlack(x, y)) {
                            callJavaScript("createBlackPiece", convertToOneDimensional(x, y));
                        } else {
                            callJavaScript("createWhitePiece", convertToOneDimensional(x, y));
                        }
                    } else {
                        if (board.getPieceFromPosition(new Position(x, y)) == null) {
                            callJavaScript("removePiece", convertToOneDimensional(x, y));
                        }
                    }
                }
            }
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

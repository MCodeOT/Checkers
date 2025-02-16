package de.check.checkers.utils;

import de.check.checkers.structures.Board;
import de.check.checkers.structures.Player;
import de.check.checkers.structures.Position;

public class Controller {
    Player playerBlack;
    Player playerWhite;

    Board board;

    private boolean someoneLost = false;

    public Controller() {

    }

    // Creates Players and Board
    public void startGame() {
        // Create Players
        playerBlack = new Player(true);
        playerWhite = new Player(false);

        // Create a new Board
        board = new Board();

    }

//    public void movePiece(Position currentPos, Position targetPos) {
//        // Check if Move is legit; if yes: do move ,if not: give notification
//        if (moveIsLegit()) {
//            board.movePiece(currentPos, targetPos);
//            // Check if someone has won; if yes: trigger win screen, if not: do nothing
//            if (someoneWon()) {
////                whoWon()
//            }
//        } else {
//            System.out.println("This move is not available, please pick another one.");
//            //
//        }
//
//
//    }
//
//    public boolean moveIsLegit() {
//
//        return false;
//    }
//
//    public boolean someoneWon() {
//
//        return false;
//    }
//
//    public Player whoWon() {
//
//        return null;
//    }


}

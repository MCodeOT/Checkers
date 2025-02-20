package de.check.checkers.utils;

import de.check.checkers.structures.Board;
import de.check.checkers.structures.Piece;
import de.check.checkers.structures.Player;
import de.check.checkers.structures.Position;

public class Controller {
    Player playerBlack;
    Player playerWhite;

    Board board;
    int boardSize;

//    private boolean someoneLost = false;

    public Controller() {
        // Create Players
        playerBlack = new Player(true);
        playerWhite = new Player(false);

        // Create a new Board
        this.boardSize = 8;
        board = new Board(boardSize);
    }

//    public int movePiece(Position currentPos, Position targetPos) {
//        // Check if Move is legit; if yes: do move, if not: give notification
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
//        return 0;
//    }
//
//    public boolean moveIsLegit(Position currentPos, Position targetPos) {
//        boolean returnValue = false;
//
//
//        /*
//
//        Check for each player separately if the direction is correct
//        Normal Pieces can only move and capture forwards one field except if it is a chain of captures
//        Crowned Pieces can move and capture forwards and backwards and they can move as many fields as they want
//
//    / /        If Normal Piece reaches the last row, they become crowned
//
//        1. Check if there is a Piece on curPos
//        2. Check if the Piece on curPos is the color of the player who's current turn it is
//        3. Check if move (isCrowned or not & isBlack or not)
//            - Move isNotCrowned & isBlack
//                - diagonal top right and diagonal top left, as long as there is no piece on tarPos
//                - tarPos.y - curPos.y > 0 && Math.abs(tarPos.x - curPos.x) == 1
//            - Move isNotCrowned & isNotBlack
//                - diagonal bottom right and diagonal bottom left, as long as there is no piece on tarPos
//                - tarPos.y - curPos.y < 0 && Math.abs(tarPos.x - curPos.x) == 1
//            - Move isCrowned (Piece color is not important here)
//                - diagonal every direction, as long as there are no pieces between curPos and tarPos
//                -
//        4. Check if simple capture (isCrowned or not & isBlack or not)
//            - Simple Capture isNotCrowned & isBlack:
//            - Simple Capture isNotCrowned & isNotBlack:
//            - Simple Capture isCrowned & isBlack:
//            - Simple Capture isCrowned & isNotBlack:
//        5. Check if chained capture (isCrowned or not & isBlack or not)
//            - Chained Capture isNotCrowned & isBlack:
//            - Chained Capture isNotCrowned & isNotBlack:
//            - Chained Capture isCrowned & isBlack:
//            - Chained Capture isCrowned & isNotBlack:
//        6. Check if Piece (if isNotCrowned) after move isCrowned
//        7. Check if Player has won after move
//
//        */
//
//
//        if (isBlack()) {
//
//            if (targetPos.getY() - currentPos.getY() > 0) {
//
//            }
//
//        } else {
//
//        }
//        return returnValue;
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

    public int getBoardSize() {
        return boardSize;
    }

    public Piece getPieceFromPosition(Position pos) {
        return board.getPieceFromPosition(pos);
    }

    public boolean isPieceBlack(Piece piece) {
        return piece.isBlack();
    }

}

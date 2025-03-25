package de.check.checkers.utils;

import de.check.checkers.structures.Board;
import de.check.checkers.structures.Piece;
import de.check.checkers.structures.Position;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Board board;
    private int boardSize;
    private Position firstClickedPos;
    private List<Position> captureQueue;

    public Controller(int boardSize) {
        this.boardSize = boardSize;
        this.captureQueue = new ArrayList<Position>();
//         Create a new Board
        this.board = new Board(boardSize);
    }

    /**
     * Handles the current click.
     *
     * @param currentPos The current clicked {@link Position}
     * @return {@code int} code depedant on the successfulness of the move
     */
//    public int handlePositionTransmission(Position currentPos) {
//        if (!isPositionValid()) return -1;
//
//        // Return fitting style int code.
//        // Example: return 1 :: move successful
//        // Check if something was selected before
//        //      If not:
//        //          firstClickedPos is the current clicked position
//        //      Else:
//        //          If the current pos is the previously saved pos:
//        //              Remove the selection
//        //              Return unhighlight code
//        //          Else:
//        //              Handle second click, validate the move, move
//        // Überprüfe Capture Zwang
//
//
//        if (firstClickedPos != null) {
//            if (currentPos == firstClickedPos) {
//                firstClickedPos = null;
//                // return unhighlight code
//            } else {
//                if (getPieceFromPosition(currentPos) == null) {
//                    if (isNormalMoveValid(firstClickedPos, currentPos)) {
//                        movePiece(firstClickedPos, currentPos);
//                    } else if (isCaptureValid()) {
//                        captureQueue.add(currentPos);
//                    }
//                    /* ALARM! DANGEROUS CODE AT RUNTIME LEVEL EXECUTION ERROR */
//                    firstClickedPos = null;
//                }
//            }
//        }else{
//            if(getPieceFromPosition(currentPos)!=null){
//                captureQueue.add(currentPos);
//            }
//        }
//        return 0;
//    }

    /**
     * Checks if the current move between a start and end position is valid,
     * by checking
     * [[[INSERT REAL DOC]]]]
     * if the square delta is 1.
     *
     * @param currentPos The current {@link Position} the move starts from
     * @param targetPos  The target {@link Position} where the move ends
     * @return {@code true} if the move is valid
     */
    private boolean isNormalMoveValid(Position currentPos, Position targetPos) {
        boolean returnValue = false;
        Piece currentPiece = board.getPieceFromPosition(currentPos);
        int curPosX = currentPos.getX();
        int curPosY = currentPos.getY();
        int tarPosX = targetPos.getX();
        int tarPosY = targetPos.getY();

        if (currentPiece.isBlack() && !currentPiece.isCrowned() && ((tarPosY - curPosY) > 0)) {
            returnValue = true;
        } else if (!currentPiece.isBlack() && !currentPiece.isCrowned()) {
            returnValue = true;
        } else if (currentPiece.isBlack() && currentPiece.isCrowned()) {
            returnValue = true;
        } else if (!currentPiece.isBlack() && currentPiece.isCrowned()) {
            returnValue = true;
        }

        return returnValue;
    }

    /**
     * Checks if the current {@link Position} is valid. It checks if there is a {@link Piece},
     * on the current {@link Position} and if the {@link Piece} is from the player whose turn
     * it is.
     * @param currentPos The current {@link Position} a move or capture starts from
     * @return
     */
    private boolean isCurrentPositionValid(Position currentPos) {

        return false;
    }

    /**
     * Checks if a capture is valid.
     *
     * @param currentPos The current {@link Position} the capture starts from
     * @param targetPos  The target {@link Position} where the capture ends
     * @return {@code true} if the capture is valid
     */
    private boolean isCaptureValid(Position currentPos, Position targetPos) {
        return false;
    }

    /**
     * Executes the move on the board.
     *
     * @param currentPos The current {@link Position} the move starts from
     * @param targetPos  The target {@link Position} where the move ends
     */
    private void movePiece(Position currentPos, Position targetPos) {
        board.movePiece(currentPos, targetPos);
    }

    /**
     * Returns the Piece from a given Position.
     *
     * @param pos The {@link Position} of the
     * @return a {@link Piece}
     */
    private Piece getPieceFromPosition(Position pos) {
        return board.getPieceFromPosition(pos);
    }

    /**
     * Checks if a {@link Piece} is black.
     *
     * @param piece The {@link Piece}, which should be checked
     * @return {@code true} if {@link Piece} is black
     */
    private boolean isPieceBlack(Piece piece) {
        return piece.isBlack();
    }

    /**
     * Returns the board.
     *
     * @return a {@link Board}
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the selected board size.
     *
     * @return The selected board size
     */
    public int getBoardSize() {
        return boardSize;
    }

}

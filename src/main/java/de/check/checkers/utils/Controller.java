package de.check.checkers.utils;

import de.check.checkers.structures.Board;
import de.check.checkers.utils.PTC;
import de.check.checkers.structures.Piece;
import de.check.checkers.structures.Position;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static final Controller instance = new Controller();

    private Board board;
    private int boardSize;
    private boolean isBlacksTurn;
    private Position firstClickedPos;
    private List<Position> captureQueue;

    private Controller() {
        this.captureQueue = new ArrayList<Position>();
        this.isBlacksTurn = true;
    }

    public static Controller getInstance() {
        return instance;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
        this.board = Board.getInstance();
        this.board.createBoard(boardSize);
    }

    /**
     * Handles the current click.
     *
     * @param currentPos The current clicked {@link Position}
     * @return {@code int} code dependent on the successfulness of the move
     */
    public int handlePositionTransmission(Position currentPos) {
        if(isPieceBlack(getPieceFromPosition(currentPos)) != isBlacksTurn) {
            return PTC.FAILURE_WRONG_PLAYER_SELECTED.ordinal();
        }
        if (firstClickedPos != null) {
            if (currentPos.getX() == firstClickedPos.getX() && currentPos.getY() == firstClickedPos.getY()) {
                firstClickedPos = null;
                return PTC.FAILURE_BOTH_POSITIONS_IDENTICAL.ordinal();
            } else {
                if (getPieceFromPosition(currentPos) == null) {
                    if (isNormalMoveValid(firstClickedPos, currentPos)) {
                        movePiece(firstClickedPos, currentPos);
                        return PTC.SUCCESSFUL_GENERIC_AVAILABLE_MOVE.ordinal();
                    } else if (isCaptureValid(firstClickedPos, currentPos)) {
                        captureQueue.add(currentPos);
                    }
                    /* ALARM! DANGEROUS CODE AT RUNTIME LEVEL EXECUTION ERROR */
                    firstClickedPos = null;
                }
            }
        }else{
            if(getPieceFromPosition(currentPos)!=null){
                captureQueue.add(currentPos);
                firstClickedPos = currentPos;
                return PTC.SUCCESSFUL_FIRST_CLICK.ordinal();
            }
        }
        return PTC.FAILURE_GENERIC_UNAVAILABLE_MOVE.ordinal();
    }

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
        Piece currentPiece;
        int curPosX = currentPos.getX();
        int curPosY = currentPos.getY();
        int tarPosX = targetPos.getX();
        int tarPosY = targetPos.getY();

//        Check if the current position is valid and the target position is on the board and without a piece on it
        if (isCurrentPositionValid(currentPos) && isPositionOnBoard(targetPos) && isPositionEmpty(targetPos)) {
            currentPiece = board.getPieceFromPosition(currentPos);
        } else {
            return false;
        }

//        Check if the conditions are right for the normal move pattern
//        General condition: pieceColor && crownedStatus && MovePattern + additional checks

//        Piece is black and not crowned
        if (currentPiece.isBlack() && !currentPiece.isCrowned() && ((tarPosY - curPosY) == 1)
                && ((tarPosX - curPosX == 1) || (tarPosX - curPosX == -1))) {
            returnValue = true;

//        Piece is white and not crowned
        } else if (!currentPiece.isBlack() && !currentPiece.isCrowned() && (tarPosY - curPosY == -1)
                && ((tarPosX - curPosX == 1) || (tarPosX - curPosX == -1))) {
            returnValue = true;

//        Color of piece doesn't matter and it is crowned
        } else if (currentPiece.isCrowned() && isTargetPosInCrownedPossibleNormalMovesList(currentPos, targetPos)) {
            returnValue = true;
        }

        return returnValue;
    }

    /**
     * Checks if a capture is valid.
     *
     * @param currentPos The current {@link Position} the capture starts from
     * @param targetPos  The target {@link Position} where the capture ends
     * @return {@code true} if the capture is valid
     */
    private boolean isCaptureValid(Position currentPos, Position targetPos) {
//        #wip
        return false;
    }

    /**
     * Checks if the current {@link Position} is valid. It checks if there is a {@link Piece},
     * on the current {@link Position} and if the {@link Piece} is from the player whose turn
     * it is.
     *
     * @param currentPos The current {@link Position} a move or capture starts from
     * @return {@code true} if {@link Position} is valid
     */
    private boolean isCurrentPositionValid(Position currentPos) {
        boolean returnValue = false;
        Piece piece = getPieceFromPosition(currentPos);

        if (isPositionEmpty(currentPos)) {
            returnValue = false;
        }
        if (!isPositionOnBoard(currentPos)) {
            returnValue = false;
        }
        if (piece.isBlack() == isBlacksTurn) {
            returnValue = true;
        }
        if(!piece.isBlack() == !isBlacksTurn) {
            returnValue = true;
        }

        return returnValue;
    }

    /**
     * Checks if the provided {@link Position} is in bounds of the generated {@link Board}.
     *
     * @param pos The {@link Position} which should be checked
     * @return {@code true} if the {@link Position} is in bounds of the {@link Board}
     */
    private boolean isPositionOnBoard(Position pos) {
        return board.isPositionOnBoard(pos);
    }

    /**
     * Checks if the target {@link Position} is part of the normal move list for a crowned {@link Piece}
     *
     * @param currentPos
     * @param targetPos
     * @return {@code true} if target {@link Position} is part of the normal move list for the crowned {@link Piece}
     */
    private boolean isTargetPosInCrownedPossibleNormalMovesList(Position currentPos, Position targetPos) {
        ArrayList<Position> crownedPossibleNormalMovesList = createCrownedPossibleNormalMovesList(currentPos);

        for (Position pos : crownedPossibleNormalMovesList) {
            if (targetPos.equals(pos)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Creates a {@link Position} List which contains all the possible positions a crowned {@link Piece}
     * can move on from their current {@link Position}.
     *
     * @param currentPos The {@link Position} of the crowned {@link Piece} for which the List should be
     *                   created
     * @return an {@link ArrayList} wich contains all possible normal moves for the crowned {@link Piece}
     * on the provided {@link Position}
     */
    private ArrayList<Position> createCrownedPossibleNormalMovesList(Position currentPos) {
        ArrayList<Position> crownedPossibleNormalMovesList = new ArrayList<>();
        int curPosX = currentPos.getX();
        int curPosY = currentPos.getY();
        boolean pieceAtPlusPlus = false;
        boolean pieceAtPlusMinus = false;
        boolean pieceAtMinusPlus = false;
        boolean pieceAtMinusMinus = false;

//        Create list with all possible moves
        for (int i = 1; i < 30; i++) {
            Position posXPlusIYPlusI = new Position(curPosX + i, curPosY + i);
            Position posXPLusIYMinusI = new Position(curPosX + i, curPosY - i);
            Position posXMinusIYPlusI = new Position(curPosX - i, curPosY + i);
            Position posXMinusIYMinusI = new Position(curPosX - i, curPosY - i);

//            Position plus plus: upper right diagonal
            if (isPositionOnBoard(posXPlusIYPlusI) && getPieceFromPosition(posXPlusIYPlusI) == null && !pieceAtPlusPlus) {
                crownedPossibleNormalMovesList.add(posXPlusIYPlusI);
            } else if (isPositionOnBoard(posXPlusIYPlusI) && getPieceFromPosition(posXPlusIYPlusI) != null) {
                pieceAtPlusPlus = true;

//            Position plus minus: lower right diagonal
            } else if (isPositionOnBoard(posXPLusIYMinusI) && getPieceFromPosition(posXPLusIYMinusI) == null && !pieceAtPlusMinus) {
                crownedPossibleNormalMovesList.add(posXPLusIYMinusI);
            } else if (isPositionOnBoard(posXPLusIYMinusI) && getPieceFromPosition(posXPLusIYMinusI) != null) {
                pieceAtPlusMinus = true;

//            Position minus plus: upper left diagonal
            } else if (isPositionOnBoard(posXMinusIYPlusI) && getPieceFromPosition(posXMinusIYPlusI) == null && !pieceAtMinusPlus) {
                crownedPossibleNormalMovesList.add(posXMinusIYPlusI);
            } else if (isPositionOnBoard(posXMinusIYPlusI) && getPieceFromPosition(posXMinusIYPlusI) != null) {
                pieceAtMinusPlus = true;

//            Position minus minus: lower left diagonal
            } else if (isPositionOnBoard(posXMinusIYMinusI) && getPieceFromPosition(posXMinusIYMinusI) == null && !pieceAtMinusMinus) {
                crownedPossibleNormalMovesList.add(posXMinusIYMinusI);
            } else if (isPositionOnBoard(posXMinusIYMinusI) && getPieceFromPosition(posXMinusIYMinusI) != null) {
                pieceAtMinusMinus = true;
            }
        }

        return crownedPossibleNormalMovesList;
    }

    /**
     * Checks if a {@link Position} is empty, which means it has no {@link Piece} on it.
     *
     * @param pos The {@link Position} which gets checked
     * @return {@code true} when {@link Position} is empty
     */
    private boolean isPositionEmpty(Position pos) {
        return getPieceFromPosition(pos) == null;
    }

    /**
     * Executes the move on the board.
     *
     * @param currentPos The current {@link Position} the move starts from
     * @param targetPos  The target {@link Position} where the move ends
     */
    private void movePiece(Position currentPos, Position targetPos) {
        board.movePiece(currentPos, targetPos);
        togglePlayerTurn();
    }

    private void togglePlayerTurn() {
        isBlacksTurn = !isBlacksTurn;
    }

    /**
     * Returns the {@link Piece} from a given {@link Position}. Returns null if there is no {@link Piece} on the
     * {@link Position}
     *
     * @param pos The {@link Position} where the {@link Piece} is returned from
     * @return the {@link Piece} from the provided {@link Position}
     */
    public Piece getPieceFromPosition(Position pos) {
        return board.getPieceFromPosition(pos);
    }

    /**
     * Checks if a {@link Piece} is black.
     *
     * @param piece The {@link Piece}, which should be checked
     * @return {@code true} if {@link Piece} is black
     */
    public boolean isPieceBlack(Piece piece) {
        return piece.isBlack();
    }

    /**
     * Checks if a {@link Piece} is crowned
     *
     * @param piece The {@link Piece}, which should be checked
     * @return {@code true} if {@link Piece} is crowned
     */
    public boolean isPieceCrowned(Piece piece) {
        return piece.isCrowned();
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

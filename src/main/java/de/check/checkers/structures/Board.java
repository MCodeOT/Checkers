package de.check.checkers.structures;

import java.util.ArrayList;

public class Board {
    private static final Board instance = new Board();

    private ArrayList<Piece> board;
    private int size;

    private Board() {}

    public static Board getInstance() {
        return instance;
    }

    public void createBoard(int size) {
        this.size = size;
        this.board = new ArrayList<Piece>();

        boolean isBlack = true;
        byte id = 0;
        for (int y = 0; y < size; y++) {
            int adder = 0;
            if (y == size / 2 - 1) {
                isBlack = !isBlack;
            }
            for (int x = 0; x < size; x += 2) {
                if (y % 2 == 0) {
                    adder = 0;
                } else {
                    adder = 1;
                }
                if (!(y == size / 2 || y == size / 2 - 1)) {
                    Position curPos = new Position(x + adder, y);

                    this.board.add(new Piece(isBlack, id, curPos));
                    id++;
                }
            }
        }
    }

    public ArrayList<Piece> getBoard() {
        return board;
    }

    public int getSize() {
        return size;
    }

    public Piece getPieceFromPosition(Position pos) {
        Piece returnValue = null;

        for (Piece piece : board) {
            if (pos.equals(piece.getCurrentPosition())) {
                returnValue = piece;
            }
        }

        return returnValue;
    }

    public boolean isPositionOnBoard(Position pos) {
        return pos.getX() < size && pos.getX() >= 0 && pos.getY() < size && pos.getY() >= 0;
    }

    public void deletePieceFromBoard(Position currentPos) {
        board.remove(getPieceFromPosition(currentPos));
    }

    public void movePiece(Position currentPos, Position targetPos) {
        int x = currentPos.getX();
        int y = currentPos.getY();
        int nextX = targetPos.getX();
        int nextY = targetPos.getY();

        for (Piece p : board) {
            Position pos = p.getCurrentPosition();
            if (pos.equals(currentPos)) {
                p.setCurrentPosition(targetPos);
                break;
            }
        }

    }

    public void makePieceOnPositionCrowned(Position pos) {
        getPieceFromPosition(pos).setCrowned(true);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = size - 1; y >= 0; y--) {
            builder.append("|");
            for (int x = 0; x < size; x++) {
                Position pos = new Position(x, y);

                boolean isPieceThere = false;


                for (Piece p : board) {
                    if (pos.equals(p.getCurrentPosition())) {
                        isPieceThere = true;
                        builder.append(p);
                        builder.append(" |");
                    }
                }
                if (!isPieceThere) {
                    builder.append("     |");
                }


            }
            builder.append("\n");
        }

        return builder.toString();
    }

}
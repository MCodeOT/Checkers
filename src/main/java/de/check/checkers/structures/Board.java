package de.check.checkers.structures;

import java.util.ArrayList;

public class Board {

    public Board() {

        int size = 8;
        ArrayList<Piece> board = new ArrayList<Piece>();
        byte id = 0;
        boolean isBlack = false;

        for (int y = 0; y < size; y++) {

            int oddAdjuster = y % 2;

            for (int x = 0; x < size; x += 2) {

                if (y != size / 2 || y != size / 2 - 1) {
                    Position curPos = new Position(x + oddAdjuster, y);
                    if (y < size/2 - 1) {
                        isBlack = true;
                    } else {
                        isBlack = false;
                    }
                    board.add(new Piece(isBlack, id, curPos));
                    id++;
                }

            }
        }

    }

}

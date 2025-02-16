package de.check.checkers.structures;

public class Player {
    // Attributes
    private boolean isBlack;
    private boolean hasLost;
    private byte pieceCount;

    // Constructor
    public Player(boolean isBlack) {
        this.isBlack = isBlack;
    }

    // Methods
    // Getter
    public boolean hasLost(){
        return hasLost;
    }

    // Setter
    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }

    // Other methods
    public boolean determineHasLost() {

//        if (pieceCount < 1) {
//            setHasLost(true);
//        }

        return hasLost;
    }

}

package de.check.checkers.structures;

import de.check.checkers.structures.Position;


public class Piece {
    private boolean isBlack;
    private boolean isCrowned;
    private byte id;
    private Position currentPosition;

    public Piece(boolean isBlack, byte id, Position currentPosition) {
        this.isBlack = isBlack;
        this.id = id;
        this.currentPosition = currentPosition;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public boolean isCrowned() {
        return isCrowned;
    }

    public void setCrowned(boolean isCrowned) {
        this.isCrowned = isCrowned;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String toString(){
        StringBuilder sb=new StringBuilder();
        if(isBlack){
            sb.append("B");
        }else{
            sb.append("W");
        }
        if(isCrowned){
            sb.append("C");
        }else{
            sb.append(" ");
        }
        if(id<10){
            sb.append("0");
        }
        sb.append(id);
        return (sb.toString());
    }
}
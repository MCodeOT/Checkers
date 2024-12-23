package de.check.checkers.structures;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y){

        this.x=x;
        this.y=y;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof Position){
            Position pos=(Position)object;
            if(this.x==pos.getX()){
                if(this.y==pos.getY()){
                    return true;
                }
            }
        }
        return false;
    }
}
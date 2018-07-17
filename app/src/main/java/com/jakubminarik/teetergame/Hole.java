package com.jakubminarik.teetergame;

public class Hole {

    private Ball.Point2D positionInMeters;

    public Hole (int x, int y){
        this.positionInMeters = new Ball.Point2D(x, y);
    }
    // alt + insert - getters and setters
    public Ball.Point2D getPositionInMeters() {
        return positionInMeters;
    }

    public void setPositionInMeters(Ball.Point2D positionInMeters) {
        this.positionInMeters = positionInMeters;
    }
}

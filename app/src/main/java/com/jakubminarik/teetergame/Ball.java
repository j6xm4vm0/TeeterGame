package com.jakubminarik.teetergame;

public class Ball {

    public int radius;
    private int x, y;
    private float velocityX = 0f;
    private float velocityY = 0f;

    public Ball(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

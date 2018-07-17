package com.jakubminarik.teetergame;

import java.util.ArrayList;

public class Level {

    private int width, height;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private ArrayList<Hole> holes = new ArrayList<>();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    private Ball.Point2D startingPosition;
    private Ball.Point2D endPosition;

    // alt + insert - getters and setters holes, obstacles, start,end
    public ArrayList<Hole> getHoles() {
        return holes;
    }

    public void setHoles(ArrayList<Hole> holes) {
        this.holes = holes;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public Ball.Point2D getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(Ball.Point2D startingPosition) {
        this.startingPosition = startingPosition;
    }

    public Ball.Point2D getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Ball.Point2D endPosition) {
        this.endPosition = endPosition;
    }

    public boolean hasStartingPosition(){
        return startingPosition != null;
    }
    public boolean hasEndingPosition(){
        return endPosition != null;
    }
}

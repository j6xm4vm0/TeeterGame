package com.jakubminarik.teetergame;

public class Ball {

    public int radius;
    private float velocityX = 0f;
    private float velocityY = 0f;
    private Point2D positionPoint;

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public static class Point2D {
        private float x, y;

        public Point2D(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }

    public Point2D getPositionPoint() {
        return positionPoint;
    }

    public void setPositionPoint(Point2D positionPoint) {
        this.positionPoint = positionPoint;
    }
}

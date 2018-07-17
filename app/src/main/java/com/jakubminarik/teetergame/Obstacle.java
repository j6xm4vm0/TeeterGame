package com.jakubminarik.teetergame;

public class Obstacle {

    private int x, y, x2, y2;

    // alt + insert - getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public void handleCollision(Ball ball, int density) {
        //get ball position
        Ball.Point2D ballPositionInMeters = ball.getPositionPoint();

        //convert position to pixels
        Ball.Point2D ballPosition = new Ball.Point2D(
                SensorHandler.metersToPixels(ballPositionInMeters.getX(), density),
                SensorHandler.metersToPixels(ballPositionInMeters.getY(), density));

        //left side of obstacle
        if (ballPosition.getX() > x - ball.getRadius()
                && ballPosition.getX() < x + ball.getRadius()
                && ballPosition.getY() > y - ball.getRadius()
                && ballPosition.getY() < y2 + ball.getRadius()) {
            ball.getPositionPoint().setX(SensorHandler.pixelsToMeters(x - ball.getRadius(), density));
            ball.setVelocityX(ball.getVelocityX() * (-SensorHandler.REFLECTION));
        }

        //todo individual work: solve at least one of the remaining sides
    }
}

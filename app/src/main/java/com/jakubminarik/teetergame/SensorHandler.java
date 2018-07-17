package com.jakubminarik.teetergame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.WindowManager;

public class SensorHandler implements SensorEventListener {

    private static final float FRICTION = 0.95f;
    public static final float REFLECTION = 0.99f;
    private Ball ball;

    private long lastMillis;
    private final float NOISE = 0.35f;
    private final static float alpha = 0.8f;
    private float[] gravity = new float[]{0f, 0f, 0f};
    private final float GRAVITY = 9.8f; // Earth gravity acceleration

    private float width, height;
    public int density; // DPI - dots per inch (PPI pixels per inch)
    private int orientation;
    private Level level;

    // 10:10

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        long nowMillis = System.currentTimeMillis();

        //this is how to get rid of the noise of sensor
        float[] clearValues = new float[3];
        clearValues[0] = clear(sensorEvent.values[0]);
        clearValues[1] = clear(sensorEvent.values[1]);
        clearValues[2] = clear(sensorEvent.values[2]);

        //now lets isolate just the gravity from values
        // low pass filter
        // we use 80% of value from previous measurement and 20% of new values
        gravity[0] = alpha * gravity[0] + (1 - alpha) * clearValues[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * clearValues[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * clearValues[2];

        // normalization
        // gravity[0] = -1.2, gravity[1] = 0.8, gravity[2] = 7.3
        // gravity[0]+gravity[1] + gravity[2] = 1.0

        float sum = Math.abs(gravity[0]) + Math.abs(gravity[1]) + Math.abs(gravity[2]);
        // in sum is going to be something like (1.2 + 0.8 + 7.3)

        // conversion to 9.8 gravity acceleration by normalized values
        // the whole thing will be much smoother after this process ;)
        gravity[0] = (gravity[0] / sum * GRAVITY);
        gravity[1] = (gravity[1] / sum * GRAVITY);
        gravity[2] = (gravity[2] / sum * GRAVITY);

        // getting delta of passed time - from milliseconds to second
        float deltaTime = (float) (nowMillis - lastMillis) / 1000f;
        // why seconds? its because acceleration in m/s


        // getting the new velocity of ball from values
        // v = a*t    (v = velocity, a = acceleration, t = time)
        float newVelocityX = gravity[0] * deltaTime;
        float newVelocityY = gravity[1] * deltaTime;

        float nowFriction;
        if (gravity[0] > -NOISE && gravity[0] < NOISE && gravity[1] > -NOISE && gravity[1] < NOISE) {
            nowFriction = 0.97f;
        } else {
            nowFriction = FRICTION;
        }

        float xValue, yValue;
        if (orientation == Surface.ROTATION_0) {
            xValue = newVelocityX;
            yValue = newVelocityY;
        } else if (orientation == Surface.ROTATION_90) {
            xValue = -newVelocityY;
            yValue = newVelocityX;
        } else if (orientation == Surface.ROTATION_180) {
            xValue = -newVelocityX;
            yValue = -newVelocityY;
        } else {
            xValue = newVelocityY;
            yValue = -newVelocityX;
        }

        //v = v0 + a*t
        float velX = ball.getVelocityX() * nowFriction + xValue;
        float velY = ball.getVelocityY() * nowFriction + yValue;

        ball.setVelocityX((alpha * ball.getVelocityX()) + (1 - alpha) * velX);
        ball.setVelocityY((alpha * ball.getVelocityY()) + (1 - alpha) * velY);

        lastMillis = nowMillis;

        // collision detection here - 4 screen sides

        //move ball according to velocity
        Ball.Point2D ballPosition = ball.getPositionPoint();
        ballPosition.setX(ballPosition.getX() - velX * deltaTime);
        ballPosition.setY(ballPosition.getY() + velY * deltaTime);

        if (ballPosition.getX() < pixelsToMeters(ball.getRadius(), density)) {
            // LEFT
            ballPosition.setX(pixelsToMeters(ball.getRadius(), density));
            ball.setVelocityX(ball.getVelocityX() * (-REFLECTION));
        } else if (ballPosition.getY() < pixelsToMeters(ball.getRadius(), density)) {
            // TOP
            ballPosition.setY(pixelsToMeters(ball.getRadius(), density));
            ball.setVelocityY(ball.getVelocityY() * (-REFLECTION));
        } else if (ballPosition.getX() > (width)) {
            // RIGHT
            ballPosition.setX(width);
            ball.setVelocityX(ball.getVelocityX() * (-REFLECTION));
        } else if (ballPosition.getY() > (height)) {
            // BOTTOM
            ballPosition.setY(height);
            ball.setVelocityY(ball.getVelocityY() * (-REFLECTION));
        }

        for (Obstacle obstacle : level.getObstacles()) {
            obstacle.handleCollision(ball, density);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    // method for initalizing hardware sensors of device
    public void init(Context context, SurfaceView surfaceView, Level level) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        orientation = display.getRotation();

        // lets fill width, height and density
        density = context.getResources().getDisplayMetrics().densityDpi;

        // this is how we get sensor manager from system of device
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        ball = new Ball();
        ball.setPositionPoint(new Ball.Point2D(pixelsToMeters(100, density), pixelsToMeters(100, density)));
        ball.setRadius(50);

        width = surfaceView.getWidth() - ball.getRadius();
        height = surfaceView.getHeight() - ball.getRadius();

        // conversion to metters
        width = pixelsToMeters((int) width, density);
        height = pixelsToMeters((int) height, density);

        this.level = level;
    }

    private float clear(float f) {
        return (f < NOISE && f > -NOISE) ? 0 : f;
    }

    public static float pixelsToMeters(int pixelsCount, int density) {
        // DPI (PPI) pixels per inch
        return ((float) pixelsCount / (float) density / 39f);
        // from pixels to metters
    }

    public static int metersToPixels(float metersCount, int density) {
        // from meters to pixels
        return (int) (metersCount * 39f * (float) density);
    }

    public Ball.Point2D getBallPosition() {
        return new Ball.Point2D(
                metersToPixels(ball.getPositionPoint().getX(), density)
                , metersToPixels(ball.getPositionPoint().getY(), density)
        );
    }
}

package com.jakubminarik.teetergame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceView;

public class SensorHandler implements SensorEventListener {

    private Ball ball;

    private long lastMillis;
    private final float NOISE = 0.35f;
    private final static float alpha = 0.8f;
    private float[] gravity = new float[]{0f, 0f, 0f};

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

        lastMillis = System.currentTimeMillis();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    // method for initalizing hardware sensors of device
    public void init(Context context, SurfaceView surfaceView) {
        // this is how we get sensor manager from system of device
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        ball = new Ball();
        ball.setPositionPoint(new Ball.Point2D(100, 100));
    }

    private float clear(float f) {
        return (f < NOISE && f > -NOISE) ? 0 : f;
    }

    public Ball.Point2D getBallPosition() {
        //return the ball position
        return ball.getPositionPoint();
    }
}

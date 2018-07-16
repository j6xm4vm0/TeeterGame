package com.jakubminarik.teetergame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceView;
import android.widget.TextView;

public class SensorHandler implements SensorEventListener {

    private Ball ball;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        textView1.setTextSize(30);
        textView2.setTextSize(30);
        textView3.setTextSize(30);
        textView1.setText("" + sensorEvent.values[0]);
        textView2.setText(String.valueOf(sensorEvent.values[1]));
        textView3.setText(String.format("%.3f", sensorEvent.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    // method for initalizing hardware sensors of device
    public void init(Context context, SurfaceView surfaceView){
        // this is how we get sensor manager from system of device
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        ball = new Ball();
        ball.setPositionPoint(new Ball.Point2D(100,100));
    }

    //this method and textViews are just temporary - we will delete them later
    TextView textView1, textView2, textView3;
    public void addTextViews(TextView textView1, TextView textView2, TextView textView3){
        this.textView1 = textView1;
        this.textView2 = textView2;
        this.textView3 = textView3;
    }

    public Ball.Point2D getBallPosition() {
        //return the ball position
        return ball.getPositionPoint();
    }
}

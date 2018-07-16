package com.jakubminarik.teetergame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.SurfaceView;

public class SensorHandler implements SensorEventListener {


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    // method for initalizing hardware sensors of device
    public void init(Context context, SurfaceView surfaceView){

    }
}

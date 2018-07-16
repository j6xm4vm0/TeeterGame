package com.jakubminarik.teetergame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private Ball ball;
    private Paint paint;
    private SensorHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting activity to full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        //getSupportActionBar().setTitle(R.string.teeter);

        ball = new Ball(100, 100);

        surfaceView = findViewById(R.id.surfaceView);

        surfaceView.post(new Runnable() {
            @Override
            public void run() {
                draw();

            }
        });

        paint = new Paint();
        paint.setColor(Color.GRAY);

        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);

        handler = new SensorHandler(); // instance of our sensor handler

        handler.init(this, surfaceView);

        // https://github.com/minarja1/TeeterGame.git

        // Plans for today
        // 1. creation of new sensor manager
        // 2. showing the data from accelerometer
        // 3. using data to move the ball
        // 4. handling collisions


    }

    private void draw() {
        Canvas canvas = surfaceView.getHolder().lockCanvas();

        canvas.drawColor(Color.WHITE);

        canvas.drawCircle(ball.getX(), ball.getY(), 50, paint);

        surfaceView.getHolder().unlockCanvasAndPost(canvas);
    }
}

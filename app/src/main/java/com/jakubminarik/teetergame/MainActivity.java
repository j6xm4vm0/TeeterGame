package com.jakubminarik.teetergame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int FPS = 120;
    private SurfaceView surfaceView;
    private Paint paint;
    private SensorHandler handler;
    private Runnable runnable;
    private Handler handlerOS;
    private boolean init;

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

        surfaceView = findViewById(R.id.surfaceView);

        surfaceView.post(new Runnable() {
            @Override
            public void run() {
                draw();
                init = true;
            }
        });

        paint = new Paint();
        paint.setColor(Color.GRAY);

        handler = new SensorHandler(); // instance of our sensor handler
        handler.init(this, surfaceView);

        handlerOS = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (init) {
                    draw();
                }
                handlerOS.postDelayed(runnable, 1000 / FPS);
            }
        };
        handlerOS.postDelayed(runnable, 1000 / FPS);
    }

    @Override
    protected void onPause() {
        handlerOS.removeCallbacks(runnable);
        // todo - stop sensorHandler later
        init = false;
        super.onPause();
    }

    private void draw() {
        //obtain position of the ball from handler
        //draw ball on given position
        Canvas canvas = surfaceView.getHolder().lockCanvas();

        canvas.drawColor(Color.WHITE);

        Ball.Point2D ballPosition = handler.getBallPosition();

        canvas.drawCircle(ballPosition.getX(), ballPosition.getY(), 50, paint);

        surfaceView.getHolder().unlockCanvasAndPost(canvas);
    }
}

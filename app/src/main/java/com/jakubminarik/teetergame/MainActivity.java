package com.jakubminarik.teetergame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private Ball ball;
    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(R.string.teeter);

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
    }

    private void draw() {
        Canvas canvas = surfaceView.getHolder().lockCanvas();

        canvas.drawColor(Color.WHITE);

        canvas.drawCircle(ball.x, ball.y, 50, paint);

        surfaceView.getHolder().unlockCanvasAndPost(canvas);
    }
}

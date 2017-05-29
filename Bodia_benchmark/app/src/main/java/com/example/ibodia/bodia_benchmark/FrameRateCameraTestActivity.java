package com.example.ibodia.bodia_benchmark;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by ibodia on 5/29/2017.
 */

public class FrameRateCameraTestActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    Camera camera;
    SurfaceView camView;
    SurfaceHolder surfaceHolder;
    boolean camCondition = false;

    TextView fpsRate;
    List<Double> list;
    Timer timer;
    double fps;
    boolean isRecord;
    TextView fpsAverageValue;
    TextView fpsMinMaxValue;

    int frames = 0;
    int avgFPS = 0;
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_fps_camera_test);

        fpsRate = (TextView)findViewById(R.id.fps_rate);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        camView = (SurfaceView) findViewById(R.id.cameraPreview);
        surfaceHolder = camView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(surfaceHolder.SURFACE_TYPE_NORMAL);
        list = new ArrayList<Double>();

        fpsAverageValue = (TextView) findViewById(R.id.average_fps_value);
        fpsMinMaxValue = (TextView) findViewById(R.id.min_max_fps_values);

        timer = new Timer();
    }

    public void onClick(View view) {
        camera.setPreviewCallback(callback);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isRecord = false;
                camera.setPreviewCallback(null);
                int value = avgFPS/frames;
                fpsAverageValue.setText(Integer.toString(value));
            }
        }, 15000);
    }

    private final int MAX_SIZE = 100;
    private final double NANOS = 1000000000.0;

    LinkedList<Long> times = new LinkedList<Long>(){{
        add(System.nanoTime());
    }};
    private double fps() {
        long lastTime = System.nanoTime();
        double difference = (lastTime - times.getFirst()) / NANOS;
        times.addLast(lastTime);
        int size = times.size();
        if (size > MAX_SIZE) {
            times.removeFirst();
        }
        return difference > 0 ? times.size() / difference : 0.0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();
        camera.setDisplayOrientation(90);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(camCondition){
            camera.stopPreview();
            camCondition = false;
        }
        if (camera!=null)
        {
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
            } catch (IOException e){
                e.printStackTrace();
            }


        }
    }
    public Camera.PreviewCallback callback = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            fps = fps();
            avgFPS += fps;
            frames++;
            //fpsRate.setText(Double.toString(fps));
        }
    };

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
        camCondition = false;
    }

}

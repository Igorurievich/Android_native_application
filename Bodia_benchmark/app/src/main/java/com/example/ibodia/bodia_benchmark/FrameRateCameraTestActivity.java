package com.example.ibodia.bodia_benchmark;

import android.content.Intent;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;

/**
 * Created by ibodia on 5/29/2017.
 */

public class FrameRateCameraTestActivity extends AppCompatActivity{

    TextView fpsRate;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_fps_camera_test);
        fpsRate = (TextView) findViewById(R.id.fps_value);
    }
    public void onClick(View view) {
        dispatchTakeVideoIntent();
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            DoSmth(videoUri);
        }
    }

   private void DoSmth(Uri uriVideo)
   {
       MediaExtractor extractor = new MediaExtractor();
       int frameRate = 24;
       try {
           extractor.setDataSource(uriVideo.getPath());
           int numTracks = extractor.getTrackCount();
           for (int i = 0; i < numTracks; ++i) {
               MediaFormat format = extractor.getTrackFormat(i);
               String mime = format.getString(MediaFormat.KEY_MIME);
               if (mime.startsWith("video/")) {
                   if (format.containsKey(MediaFormat.KEY_FRAME_RATE)) {
                       frameRate = format.getInteger(MediaFormat.KEY_FRAME_RATE);
                       fpsRate.setText(Integer.toString(frameRate));
                   }
               }
           }
       } catch (IOException e) {
           e.printStackTrace();
       }finally {
           extractor.release();
       }
   }

}

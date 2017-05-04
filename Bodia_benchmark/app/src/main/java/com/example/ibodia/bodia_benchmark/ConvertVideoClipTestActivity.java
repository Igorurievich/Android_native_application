package com.example.ibodia.bodia_benchmark;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

/**
 * Created by ibodia on 5/1/2017.
 */

public class ConvertVideoClipTestActivity extends AppCompatActivity implements View.OnClickListener  {
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_convert_video_clip_test);
    }

    public void onClick(View view) {
        String dirPath = getFilesDir().getAbsolutePath() + File.separator + "newfoldername";
        File projDir = new File(dirPath);
        if (!projDir.exists()) {
            projDir.mkdirs();
        }
    }
}

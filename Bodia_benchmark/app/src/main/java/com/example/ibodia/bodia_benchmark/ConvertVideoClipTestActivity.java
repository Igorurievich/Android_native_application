package com.example.ibodia.bodia_benchmark;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ibodia.bodia_benchmark.CompressHelpers.UnZipUtility;
import com.example.ibodia.bodia_benchmark.CompressHelpers.ZipUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.zip.ZipOutputStream;

/**
 * Created by ibodia on 5/1/2017.
 */

public class ConvertVideoClipTestActivity extends AppCompatActivity implements View.OnClickListener  {
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_convert_video_clip_test);
    }

    public void onClick(View view) {

        DeleteRecursive(new File(getFilesDir().getAbsolutePath()));

        //CreateFilesForCompression(100000);
//
//        try {
//            UnZipFiles();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    void DeleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteRecursive(child);

        fileOrDirectory.delete();

    }

    private void CreateFiles(int count) throws IOException {
        Random rn = new Random();
        for (int i = 0; i < count; i++)
        {
            RandomAccessFile file = new RandomAccessFile(getFilesDir().getAbsolutePath()+File.separator + "FilesForCompression"+File.separator+"file"+i+".file", "rw");
            file.setLength(rn.nextInt(5 - 1 + 1) + 1);
        }
    }

    private void CreateFilesForCompression(int count)
    {
        String dirPath = getFilesDir().getAbsolutePath() + File.separator + "FilesForCompression";
        File projDir = new File(dirPath);
        if (!projDir.exists()) {
            projDir.mkdirs();
            try {
                CreateFiles(count);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void UnZipFiles() throws IOException {
        UnZipUtility unZipUtility = new UnZipUtility();
        unZipUtility.unZip(
                getFilesDir().getAbsolutePath() + File.separator + "ZipFile.zip",
                getFilesDir().getAbsolutePath() + File.separator + "UnCompressFiles");
    }

    private void ZipFiles()
    {
        ZipUtility zipUtility = new ZipUtility();

        zipUtility.SOURCE_FOLDER = getFilesDir().getAbsolutePath() + File.separator + "FilesForCompression";
        zipUtility.OUTPUT_ZIP_FILE = getFilesDir().getAbsolutePath() + File.separator + "ZipFile.zip";

        zipUtility.generateFileList(new File(zipUtility.SOURCE_FOLDER));
        zipUtility.zipFileOrFolder(zipUtility.OUTPUT_ZIP_FILE);
    }
}

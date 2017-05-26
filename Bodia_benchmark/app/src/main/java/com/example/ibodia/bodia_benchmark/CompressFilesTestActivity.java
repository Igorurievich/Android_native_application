package com.example.ibodia.bodia_benchmark;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibodia.bodia_benchmark.CompressHelpers.UnZipUtility;
import com.example.ibodia.bodia_benchmark.CompressHelpers.ZipUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ibodia on 5/1/2017.
 */

public class CompressFilesTestActivity extends AppCompatActivity {

    TextView firstResultText;
    TextView secondResultText;
    TextView thirdResultText;
    TextView averageTimeResultText;
    Button btnRunCompressTest;

    double compressTime;
    double unCompressTime;

    double averageCompressTime;
    double averageUnCompressTime;

    private int testNumber = 0;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_compress_files_test);

        firstResultText = (TextView) findViewById(R.id.first_compressing_test_result);
        secondResultText = (TextView) findViewById(R.id.second_compressing_test_result);
        thirdResultText = (TextView) findViewById(R.id.third_compressing_test_result);
        averageTimeResultText = (TextView) findViewById(R.id.arithmetic_main_compressing_test);
        btnRunCompressTest = (Button) findViewById(R.id.run_compress_test_button);

        copyFileOrDir("FilesForCompress");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_all_files:
                DeleteRecursive(new File(getFilesDir().getAbsolutePath()));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void EraseWhile() {
       averageCompressTime = compressFiles();
        averageUnCompressTime = unCompressFiles();
    }

    public void onStartTestClick(View v)
    {
        if (testNumber < 3) {
            EraseWhile();
            testNumber++;
            FillResults(testNumber);
            averageCompressTime = averageCompressTime + compressTime;
            averageUnCompressTime = averageUnCompressTime + unCompressTime;
        }
        if (testNumber==3){
            averageTimeResultText.setText((averageCompressTime / 3) + ", " + (averageUnCompressTime / 3));
            btnRunCompressTest.setText(getResources().getString(R.string.start_new_tests_series_with_while_btn_label));
            testNumber++;
            averageCompressTime = 0;
            averageUnCompressTime = 0;
            return;
        }
        if (btnRunCompressTest.getText() == getResources().getString(R.string.start_new_tests_series_with_while_btn_label))
        {
            btnRunCompressTest.setText(R.string.label_button_run_while);
            ClearTestsResults();
            testNumber = 0;
        }
    }

    private void ClearTestsResults()
    {
        firstResultText.setText(" ");
        secondResultText.setText(" ");
        thirdResultText.setText(" ");
        averageTimeResultText.setText(" ");
    }

    private void FillResults(int numberOfTest)
    {
        switch (numberOfTest) {
            case 1:
                firstResultText.setText(Double.toString(averageCompressTime)+"/"+Double.toString(averageUnCompressTime));
                break;
            case 2:
                secondResultText.setText(Double.toString(averageCompressTime)+"/"+Double.toString(averageUnCompressTime));
                break;
            case 3:
                thirdResultText.setText(Double.toString(averageCompressTime)+"/"+Double.toString(averageUnCompressTime));
                break;
        }
    }

    void DeleteRecursive(File fileOrDirectory)
    {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteRecursive(child);
        fileOrDirectory.delete();
    }

    public void copyFileOrDir(String path) {
        AssetManager assetManager = this.getAssets();
        String assets[] = null;
        try {
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(path);
            } else {
                String fullPath = getFilesDir() + File.separator + path;
                File dir = new File(fullPath);
                if (!dir.exists())
                    dir.mkdir();
                for (int i = 0; i < assets.length; ++i) {
                    copyFileOrDir(path + "/" + assets[i]);
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    private double compressFiles()
    {
        long tStart = System.currentTimeMillis();

        ZipFiles(getFilesDir().getAbsolutePath() + File.separator + "FilesForCompress",
                getFilesDir().getAbsolutePath() + File.separator + "CompressedFiles.zip");
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }

    private double unCompressFiles()
    {
        long tStart = System.currentTimeMillis();
        try {
            UnZipFiles(getFilesDir().getAbsolutePath() + File.separator + "CompressedFiles.zip",
                    getFilesDir().getAbsolutePath() + File.separator + "UnCompressFiles");
        } catch (IOException e) {
            e.printStackTrace();
        }
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }

    private void copyFile(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            String newFileName = getFilesDir() + File.separator + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

    private void UnZipFiles(String zipFilePath, String destDirectory) throws IOException
    {
        UnZipUtility unZipUtility = new UnZipUtility();
        unZipUtility.unZip(zipFilePath, destDirectory);
    }

    private void ZipFiles(String sourceDirectory, String outputZipFile)
    {
        ZipUtility zipUtility = new ZipUtility();
        zipUtility.zipFolder(sourceDirectory,outputZipFile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.compress_activity_menu, menu);
        return true;
    }
}

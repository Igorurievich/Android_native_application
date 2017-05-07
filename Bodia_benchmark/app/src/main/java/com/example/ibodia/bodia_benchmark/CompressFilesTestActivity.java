package com.example.ibodia.bodia_benchmark;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.ibodia.bodia_benchmark.CompressHelpers.UnZipUtility;
import com.example.ibodia.bodia_benchmark.CompressHelpers.ZipUtility;

import java.io.File;
import java.io.IOException;

/**
 * Created by ibodia on 5/1/2017.
 */

public class CompressFilesTestActivity extends AppCompatActivity {
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_compress_files_test);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.prepare_files_for_zip_tests:
                PrepareFilesForTests();
                return true;
            case R.id.clear_generated_files:
                DeleteRecursive(new File(getFilesDir().getAbsolutePath() + File.separator + "UnCompressFiles"));
                return true;
            case R.id.clear_all_files:
                DeleteRecursive(new File(getFilesDir().getAbsolutePath()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void PrepareFilesForTests()
    {
        try {
            UnZipFiles(getFilesDir().getAbsolutePath()+ File.separator +"FilesForCompress.zip",
                    getFilesDir().getAbsolutePath() + File.separator + "UnCompressFiles");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onCompressClick(View view) {

        ZipFiles(getFilesDir().getAbsolutePath() + File.separator + "UnCompressFiles",
                getFilesDir().getAbsolutePath() + File.separator + "CompressedFiles.zip");
    }
    public void onUnCompressClick(View view) {

        try {
            UnZipFiles(getFilesDir().getAbsolutePath() + File.separator + "CompressedFiles.zip",
                    getFilesDir().getAbsolutePath() + File.separator + "UnCompressFiles");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void DeleteRecursive(File fileOrDirectory)
    {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteRecursive(child);
        fileOrDirectory.delete();
    }

    private void UnZipFiles(String zipFilePath, String destDirectory) throws IOException
    {
        UnZipUtility unZipUtility = new UnZipUtility();
        unZipUtility.unZip(zipFilePath, destDirectory);
    }

    private void ZipFiles(String sourceDirectory, String outputZipFile)
    {
        ZipUtility zipUtility = new ZipUtility();
        zipUtility.OUTPUT_ZIP_FILE = outputZipFile;
        zipUtility.generateFileList(new File(sourceDirectory));
        zipUtility.zipFileOrFolder(outputZipFile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.compress_activity_menu, menu);
        return true;
    }
}

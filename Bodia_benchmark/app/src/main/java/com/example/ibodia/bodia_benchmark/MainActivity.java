package com.example.ibodia.bodia_benchmark;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner lv;
    Button btnRunTest;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();

        lv = (Spinner) findViewById(R.id.testsList);

        List<String> your_array_list = new ArrayList<String>();
        your_array_list.add(res.getString(R.string.test_name_ui_loading));
        your_array_list.add(res.getString(R.string.test_name_database_operations));
        your_array_list.add(res.getString(R.string.test_name_start_big_while));
        your_array_list.add(res.getString(R.string.test_name_compress_files));


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.test_list_item,
                your_array_list );

        lv.setAdapter(arrayAdapter);

        btnRunTest = (Button) findViewById(R.id.run_test_button);
        btnRunTest.setOnClickListener(MainActivity.this);

    }

    public void onClick(View v) {

        if(v.getId() == R.id.run_test_button)
        {
            Spinner mySpinner=(Spinner) findViewById(R.id.testsList);
            String selectedTeststring = mySpinner.getSelectedItem().toString();

            if (selectedTeststring == getString(R.string.test_name_ui_loading))
            {
                CreateUiLoadingActivity();
            }
            else if (selectedTeststring == getString(R.string.test_name_start_big_while))
            {
                CreateWhileTestActivity();
            }
            else if (selectedTeststring == getString(R.string.test_name_database_operations))
            {
                CreateDatabaseOperations();
            }
            else if (selectedTeststring == getString(R.string.test_name_compress_files))
            {
                CreateVideoClipConvertActivity();
            }
        }
    }

    private void CreateUiLoadingActivity()
    {
        Intent intent = new Intent(this, FillListViewTestActivity.class);
        startActivity(intent);
    }

    private void CreateWhileTestActivity()
    {
        Intent intent = new Intent(this, BigWhileTestActivity.class);
        startActivity(intent);
    }
    private void CreateDatabaseOperations()
    {
        Intent intent = new Intent(this, DatabaseOperationsTestActivity.class);
        startActivity(intent);
    }

    private void CreateVideoClipConvertActivity()
    {
        Intent intent = new Intent(this, CompressFilesTestActivity.class);
        startActivity(intent);
    }
}


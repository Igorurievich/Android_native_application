package com.example.ibodia.bodia_benchmark;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibodia on 5/1/2017.
 */

public class FillListViewTestActivity extends ListActivity implements View.OnClickListener {

    EditText editTextWhileCount;
    TextView firstResultText;
    TextView secondResultText;
    TextView thirdResultText;
    TextView averageTimeResultText;
    Button btnRunListViewTest;
    private int testNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_listview_test);

        List values = new ArrayList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        editTextWhileCount = (EditText) findViewById(R.id.input_listview_items_count);
        firstResultText = (TextView) findViewById(R.id.first_listview_test_result);
        secondResultText = (TextView) findViewById(R.id.second_listview_test_result);
        thirdResultText = (TextView) findViewById(R.id.third_listview_test_result);
        averageTimeResultText = (TextView) findViewById(R.id.arithmetic_main_listview_test);

        btnRunListViewTest = (Button) findViewById(R.id.addItem);
        btnRunListViewTest.setOnClickListener(FillListViewTestActivity.this);
    }

    public void onClick(View view) {
        if (testNumber < 3) {
            editTextWhileCount.setEnabled(false);
            double elapsedTime = EraseDynamiclyItemsInserting(Long.parseLong(editTextWhileCount.getText().toString()));
            testNumber++;
            FillResults(testNumber, elapsedTime);
        }
        if (testNumber==3){
            double average = (Double.parseDouble(firstResultText.getText().toString()) +
                    Double.parseDouble(secondResultText.getText().toString()) +
                    Double.parseDouble(thirdResultText.getText().toString())) / 3;
            averageTimeResultText.setText(Double.toString(average));
            btnRunListViewTest.setText(R.string.start_new_tests_series_with_while_btn_label);
            testNumber++;
            return;
        }

        if (btnRunListViewTest.getText() == getResources().getString(R.string.start_new_tests_series_with_while_btn_label))
        {
            btnRunListViewTest.setText(R.string.label_button_run_while);
            ClearTestsResults();
            testNumber = 0;
            editTextWhileCount.setEnabled(true);
        }
    }

    private double EraseDynamiclyItemsInserting(long count) {
        long tStart = System.currentTimeMillis();
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        for (long i = 0; i < count; i++) {
            String device = "Item: " + Long.toString(i);
            adapter.add(device);
            adapter.notifyDataSetChanged();
        }
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }
    private void ClearTestsResults()
    {
        firstResultText.setText(" ");
        secondResultText.setText(" ");
        thirdResultText.setText(" ");
        averageTimeResultText.setText(" ");
    }
    private void FillResults(int numberOfTest, double time)
    {
        switch (numberOfTest) {
            case 1:
                firstResultText.setText(Double.toString(time));
                break;
            case 2:
                secondResultText.setText(Double.toString(time));
                break;
            case 3:
                thirdResultText.setText(Double.toString(time));
                break;
        }
    }
}
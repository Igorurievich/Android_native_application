package com.example.ibodia.bodia_benchmark;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ibodia.bodia_benchmark.DbHelpers.DatabaseManager;
import com.example.ibodia.bodia_benchmark.DbHelpers.UserData;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ibodia on 5/1/2017.
 */

public class DatabaseOperationsTestActivity extends AppCompatActivity {

    EditText inputedRecordsCount;
    TextView firstResultText;
    TextView secondResultText;
    TextView thirdResultText;
    TextView averageTimeResultText;
    Button btnRunDBTest;

    private int testNumber = 0;

    double inseringTime;
    double updatingTime;
    double deletingTime;


    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_database_operations_test);
        DatabaseManager.init(this);

        inputedRecordsCount =  (EditText) findViewById(R.id.input_records_count);
        firstResultText = (TextView) findViewById(R.id.first_db_test_result);
        secondResultText = (TextView) findViewById(R.id.second_db_test_result);
        thirdResultText = (TextView) findViewById(R.id.third_db_test_result);
        averageTimeResultText = (TextView) findViewById(R.id.arithmetic_main_db_test);
        btnRunDBTest = (Button) findViewById(R.id.run_db_test_button);
    }

    public void onStartTestClick(View v)
    {
        if (testNumber < 3) {
            inputedRecordsCount.setEnabled(false);
            EraseDatabaseTest(Long.parseLong(inputedRecordsCount.getText().toString()));
            testNumber++;
            FillResults(testNumber);
        }
        if (testNumber==3){
//            double average = (Double.parseDouble(firstResultText.getText().toString()) +
//                    Double.parseDouble(secondResultText.getText().toString()) +
//                    Double.parseDouble(thirdResultText.getText().toString())) / 3;
//            averageTimeResultText.setText(Double.toString(average));
            btnRunDBTest.setText(R.string.start_new_tests_series_with_while_btn_label);
            testNumber++;
            return;
        }

        if (btnRunDBTest.getText() == getResources().getString(R.string.start_new_tests_series_with_while_btn_label))
        {
            btnRunDBTest.setText(R.string.label_button_run_while);
            ClearTestsResults();
            testNumber = 0;
            inputedRecordsCount.setEnabled(true);
        }
    }

    private void FillResults(int numberOfTest)
    {
        switch (numberOfTest) {
            case 1:
                firstResultText.setText(Double.toString(inseringTime)+", "+Double.toString(updatingTime)+", "+Double.toString(deletingTime));
                break;
            case 2:
                secondResultText.setText(Double.toString(inseringTime)+", "+Double.toString(updatingTime)+", "+Double.toString(deletingTime));
                break;
            case 3:
                thirdResultText.setText(Double.toString(inseringTime)+", "+Double.toString(updatingTime)+", "+Double.toString(deletingTime));
                break;
        }
    }

    private void ClearTestsResults()
    {
        firstResultText.setText(" ");
        secondResultText.setText(" ");
        thirdResultText.setText(" ");
        averageTimeResultText.setText(" ");
    }

    public double InsertUsers(long recordsCount)
    {
        long tStart = System.currentTimeMillis();
        for(int i = 0; i < recordsCount; i++)
        {
            String name = UUID.randomUUID().toString();
            String surname = UUID.randomUUID().toString();
            DatabaseManager.getInstance().addUser(new UserData(name, surname , new Date()));
        }
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }

    public double UpdateUsers()
    {
        ArrayList<UserData> userArrayList = DatabaseManager.getInstance().getAllUsers();

        long tStart = System.currentTimeMillis();
        for(int i = 0; i < userArrayList.size(); i++) {
            String name = UUID.randomUUID().toString();
            String surname = UUID.randomUUID().toString();

            UserData userData = userArrayList.get(i);

            userData.setBirthDate(new Date());
            userData.setSurname(surname);
            userData.setName(name);

            DatabaseManager.getInstance().updateUser(userData);
        }
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }

    public double DeletingUsers()
    {
        ArrayList<UserData> userArrayList = DatabaseManager.getInstance().getAllUsers();
        long tStart = System.currentTimeMillis();
        for(int i = userArrayList.get(0).getId(); i < userArrayList.size()+ userArrayList.get(0).getId(); i++)
        {
            DatabaseManager.getInstance().deleteUser(i);
        }
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }

    public void EraseDatabaseTest(long recordsCount)
    {
        inseringTime = InsertUsers(recordsCount);
        updatingTime = UpdateUsers();
        deletingTime = DeletingUsers();
    }
}

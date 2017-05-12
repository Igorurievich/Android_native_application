package com.example.ibodia.bodia_benchmark;

import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.ibodia.bodia_benchmark.DbHelpers.UserData;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * Created by ibodia on 5/1/2017.
 */

public class DatabaseOperationsTestActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getSimpleName();
    private final static int MAX_NUM_TO_CREATE = 8;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_database_operations_test);
    }

    private void doSampleDatabaseStuff(String action, TextView tv) {
        // get our dao
        RuntimeExceptionDao<UserData, Integer> simpleDao = getHelper().getSimpleDataDao();
        // query for all of the data objects in the database
        List<UserData> list = simpleDao.queryForAll();
        // our string builder for building the content-view
        StringBuilder sb = new StringBuilder();
        sb.append("Found ").append(list.size()).append(" entries in DB in ").append(action).append("()\n");

        // if we already have items in the database
        int simpleC = 1;
        for (UserData simple : list) {
            sb.append('#').append(simpleC).append(": ").append(simple).append('\n');
            simpleC++;
        }
        sb.append("------------------------------------------\n");
        sb.append("Deleted ids:");
        for (UserData simple : list) {
            simpleDao.delete(simple);
            sb.append(' ').append(simple.id);
            Log.i(LOG_TAG, "deleting simple(" + simple.id + ")");
            simpleC++;
        }
        sb.append('\n');
        sb.append("------------------------------------------\n");

        int createNum;
        do {
            createNum = new Random().nextInt(MAX_NUM_TO_CREATE) + 1;
        } while (createNum == list.size());
        sb.append("Creating ").append(createNum).append(" new entries:\n");
        for (int i = 0; i < createNum; i++) {
            // create a new simple object
            long millis = System.currentTimeMillis();
            UserData simple = new UserData(millis);
            // store it in the database
            simpleDao.create(simple);
            Log.i(LOG_TAG, "created simple(" + millis + ")");
            // output it
            sb.append('#').append(i + 1).append(": ");
            sb.append(simple).append('\n');
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                // ignore
            }
        }

        tv.setText(sb.toString());
        Log.i(LOG_TAG, "Done with page at " + System.currentTimeMillis());
    }
}

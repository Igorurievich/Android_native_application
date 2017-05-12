package com.example.ibodia.bodia_benchmark.DbHelpers;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by ibodia on 5/12/2017.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile("ormlite_config.txt");
    }
}

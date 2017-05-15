package com.example.ibodia.bodia_benchmark.DbHelpers;

import android.content.Context;
import com.j256.ormlite.stmt.DeleteBuilder;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseManager {

    private static DatabaseManager instance;
    private DatabaseHelper helper;

    public static void init(Context ctx) {
        if (null == instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    public DatabaseHelper getHelper() {
        return helper;
    }

    /**
     * Get all customer in db
     *
     * @return
     */
    public ArrayList<UserData> getAllUsers() {
        ArrayList<UserData> cats = null;
        try {
            cats = (ArrayList<UserData>) getHelper().getUserDAO().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cats;
    }

    public void addUser(UserData user) {
        try {
            getHelper().getUserDAO().create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshUser(UserData user) {
        try {
            getHelper().getUserDAO().refresh(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(UserData wishList) {
        try {
            getHelper().getUserDAO().update(wishList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser (int userId) {
        try {
            DeleteBuilder<UserData, Integer> deleteBuilder = getHelper().getUserDAO().deleteBuilder();
            deleteBuilder.where().eq("id", userId);
            deleteBuilder.delete();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
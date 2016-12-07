package com.meiji.daily.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.meiji.daily.InitApp;
import com.meiji.daily.database.table.ZhuanlanTable;

/**
 * Created by Meiji on 2016/12/7.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Daily";
    private static final int DB_VERSION = 2;
    private static final String CLEAR_TABLE_DATA = "delete from ";
    private static final String DROP_TABLE = "drop table if exists ";
    private static DatabaseHelper instance = null;
    private static SQLiteDatabase db = null;

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static synchronized DatabaseHelper getInstance() {
        if (instance == null) {
            instance = new DatabaseHelper(InitApp.AppContext, DB_NAME, null, DB_VERSION);
        }
        return instance;
    }

    public static synchronized SQLiteDatabase getDatabase() {
        if (db == null) {
            db = getInstance().getWritableDatabase();
        }
        return db;
    }

    public static synchronized void closeDatabase() {
        if (db != null) {
            db.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ZhuanlanTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL(DROP_TABLE + ZhuanlanTable.TABLENAME);
            db.execSQL(ZhuanlanTable.CREATE_TABLE);
        }
    }
}

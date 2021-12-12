package com.Kamooone.kamoooneshot;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class MyOpenHelper extends SQLiteOpenHelper {

    // データーベースのバージョン
    private static final int DATABASE_VERSION = 3;

    // データーベース情報を変数に格納
    private static final String DATABASE_NAME = "TestDB.db";
    private static final String TABLE_NAME = "testdb";
    private static final String _ID = " id";
    private static final String COLUMN_NAME_TITLE = "userName";
    private static final String COLUMN_NAME_SUBTITLE = "score";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_SUBTITLE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public MyOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                SQL_CREATE_ENTRIES
        );

        saveData(db, "未登録", 0);
        saveData(db, "未登録", 0);
        saveData(db, "未登録", 0);
        saveData(db, "未登録", 0);
        saveData(db, "未登録", 0);
        saveData(db, "未登録", 0);
        saveData(db, "未登録", 0);
        saveData(db, "未登録", 0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(
                SQL_DELETE_ENTRIES
        );
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void saveData(SQLiteDatabase db, String userName, int score){
        ContentValues values = new ContentValues();
        values.put("userName", userName);
        values.put("score", score);

        db.insert("testdb", null, values);
    }
}
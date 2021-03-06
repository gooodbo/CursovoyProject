package com.example.cursovoyproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "department staff";
    public static final String TABLE_MEMBERS = "members1";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_POSITION = "position";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TELEPHONE = "telephone";
    public static final String KEY_AUDIENCE = "audience";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_MEMBERS + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text,"
                + KEY_POSITION + " text," + KEY_EMAIL + " text,"
                + KEY_TELEPHONE + " text," + KEY_AUDIENCE + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_MEMBERS);
        onCreate(db);
    }
}
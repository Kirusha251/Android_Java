package com.example.kirill.manager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kirill on 20.11.2016.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public MyDataBaseHelper(Context context){
        super(context,"ManagerDB",null,7);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Students(" +"_id integer primary key Autoincrement,"+
                "NAME text ," +
                "SURNAME text," +
                "AGE integer," +
                "BIRTHDAY text," +
                "RATING integer," +
                "COUNTRY text," +
                "NATIONALITY text," +
                "LOGINHASH text," +
                "SELECTED text" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table Students");

        db.execSQL("create table Students(" +"_id integer primary key Autoincrement,"+
                "NAME text ," +
                "SURNAME text," +
                "AGE integer," +
                "BIRTHDAY text," +
                "RATING integer," +
                "COUNTRY text," +
                "NATIONALITY text," +
                "LOGINHASH text," +
                "SELECTED text" +
                ");");
    }
}

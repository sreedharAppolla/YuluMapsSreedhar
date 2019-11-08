package com.example.yulumapssreedhar.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OfflineStorageDatabase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME="offline.db";
    private static final int DATABASE_VERSION=1;

    private static final String OFFLINE_TABLE_NAME="offline_table";
    private static final String JSON_OBJECT="json_object";

    private static final String OFFLINE_TABLE_QUERY = "CREATE TABLE " + OFFLINE_TABLE_NAME + "(" + JSON_OBJECT + " VARCHAR(225)" +");";


    public OfflineStorageDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OFFLINE_TABLE_QUERY);
    }


    public void insertDetails(String jsonObject,SQLiteDatabase db){
        ContentValues contentValues=new ContentValues();
        contentValues.put(JSON_OBJECT,jsonObject);
        db.insert(OFFLINE_TABLE_NAME,null,contentValues);
    }

    public Cursor fetechDetails(SQLiteDatabase db){
        Cursor cursor;
        cursor=db.rawQuery("select * from "+OFFLINE_TABLE_NAME,null);
        return cursor;
    }


    public void clearRecords(SQLiteDatabase db){
        db.execSQL("delete from "+OFFLINE_TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

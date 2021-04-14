package com.example.donelistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.SpannableString;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dataList.db";
    private static final String TABLE1 = "user";
    private static final String TABLE2 = "list";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE1 + " (id INTEGER PRIMARY KEY, nama TEXT, email TEXT, password TEXT)");
        db.execSQL("CREATE TABLE " + TABLE2 + " (id INTEGER PRIMARY KEY, id_user TEXT, data TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2);
    }

    public boolean insertUser(String nama, String email, String password){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nama", nama);
        values.put("email", email);
        values.put("password", password);

        long result = sqLiteDatabase.insert(TABLE1, null, values);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean insertList(String user_id, String data){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id_user", user_id);
        values.put("data", data);

        long result = sqLiteDatabase.insert(TABLE2, null, values);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getDataUser(){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM " + TABLE1, null);
        return cursor;
    }

    public Cursor getDataList(){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM " + TABLE2, null);
        return cursor;
    }
}

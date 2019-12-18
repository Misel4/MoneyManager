package com.example.misel.moneymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "money.db";
    public static final String TABLE_NAME = "income_table";
    public static final String TABLE2_NAME = "spending_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DATES";
    public static final String COL_3 = "AMOUNT";
    public static final String COL_4 = "CATEGORY";
    public static final String S_COL_1 = "ID";
    public static final String S_COL_2 = "DATES";
    public static final String S_COL_3 = "AMOUNT";
    public static final String S_COL_4 = "CATEGORY";

    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null,1);

    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String dates = sdf.format(new Date());

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATES DATE,AMOUNT INTEGER,CATEGORY VARCHAR)"  );
        db.execSQL(" create table " + TABLE2_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATES DATE,AMOUNT INTEGER,CATEGORY VARCHAR)"  );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE2_NAME);
        onCreate(db);
    }

    public boolean insertData(String amount, String dates, CharSequence text){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,dates);
        contentValues.put(COL_3,amount);
        contentValues.put(COL_4, (String) text);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean insertSpendingData(String amount, String dates, CharSequence text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(S_COL_2,dates);
        contentValues.put(S_COL_3,amount);
        contentValues.put(S_COL_4, (String) text);
        long result = db.insert(TABLE2_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * FROM " + TABLE_NAME ,null);
        return res;
    }

}

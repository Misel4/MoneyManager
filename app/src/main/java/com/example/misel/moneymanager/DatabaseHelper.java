package com.example.misel.moneymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATES DATE,AMOUNT INTEGER,CATEGORY VARCHAR)"  );
        db.execSQL(" create table " + TABLE2_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATES DATE,AMOUNT INTEGER,CATEGORY VARCHAR)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE2_NAME);
        onCreate(db);
    }

    public boolean insertData(String amount, String date, CharSequence text){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,date);
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

    public Cursor getExpenditure(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE2_NAME,null);
        return result;
    }
    public int getIncomeBalance() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT SUM(" + COL_3 + ") FROM " + TABLE_NAME, null);
        if (data.moveToFirst()) {
            return data.getInt(0);
        }
        return 0;
    }

    public int getExpBalance(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT SUM(" + S_COL_3 + ") FROM " + TABLE2_NAME, null);
        if (data.moveToFirst()) {
            return data.getInt(0);
        }
        return 0;
    }

    public int getTotalBalance(){
        int income = getIncomeBalance();
        int expenditure = getExpBalance();
        return  income-expenditure;
    }

    public Cursor getItemID(String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_1 + " FROM " + TABLE_NAME +
                " WHERE " + COL_3 + " = '" + amount + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteItem(int id, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL_1 + " = '" + id + "'" +
                " AND " + COL_3 + " = '" + amount + "'";
        db.execSQL(query);
    }

    public Cursor getItemIDSpending(String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + S_COL_1 + " FROM " + TABLE2_NAME +
                " WHERE " + S_COL_3 + " = '" + amount + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteItemSpending(int id, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE2_NAME + " WHERE "
                + S_COL_1 + " = '" + id + "'" +
                " AND " + S_COL_3 + " = '" + amount + "'";
        db.execSQL(query);
    }

}

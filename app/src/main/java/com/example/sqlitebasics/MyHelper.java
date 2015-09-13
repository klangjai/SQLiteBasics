package com.example.sqlitebasics;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Klangjai on 13-Sep-15.
 */
public class MyHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "contacts";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_PHONE_NUMBER = "phone_number";

    public MyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTable = "CREATE TABLE %s( "+
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "%s TEXT, " +
                "%s TEXT)";

        sqlCreateTable = String.format(sqlCreateTable,TABLE_NAME,COL_ID,COL_NAME,COL_PHONE_NUMBER);
        db.execSQL(sqlCreateTable);
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME,"กลางใจ");
        cv.put(COL_PHONE_NUMBER,"111-111-1111");
        db.insert(TABLE_NAME,null,cv);

        cv = new ContentValues();
        cv.put(COL_NAME,"ธรรมนาม");
        cv.put(COL_PHONE_NUMBER,"222-222-2222");
        db.insert(TABLE_NAME,null,cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

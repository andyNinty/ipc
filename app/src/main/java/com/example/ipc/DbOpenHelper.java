package com.example.ipc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author lishibo
 * @date 2019-06-23
 * email : andy_li@swift365.com.cn
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String BOOK_DB = "book.db";
    private static final int DB_VERSION = 1;
    private static final String BOOK_TABLE = "book";
    private static final String USER_TABLE = "user";



    private static final String CREATE_BOOK_TABLE = "CREATE TABLE " + BOOK_TABLE +
            " (_id INTEGER PRIMARY KEY," + " name TEXT)";


    private static final String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE +
            " (_id INTEGER PRIMARY KEY," + " name TEXT," + " sex INT)";

    public DbOpenHelper(@Nullable Context context) {
        super(context, BOOK_DB, null, DB_VERSION);
        Log.e("--------", CREATE_BOOK_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

package com.example.ipc;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author lishibo
 * @date 2019-06-23
 * email : andy_li@swift365.com.cn
 */
public class BookProvider extends ContentProvider {

    private static final String AUTHORITIES = "com.example.ipc.BookProvider";

    public static final Uri BOOK_URI = Uri.parse("content://" + AUTHORITIES + "/book");
    public static final Uri USER_URI = Uri.parse("content://" + AUTHORITIES + "/user");

    private static final int BOOK_CODE = 0;
    private static final int USER_CODE = 1;

    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private SQLiteDatabase mDb;

    static {
        sUriMatcher.addURI(AUTHORITIES, "book", BOOK_CODE);
        sUriMatcher.addURI(AUTHORITIES, "user", USER_CODE);
    }


    public String getTableName(Uri uri) {
        String tableName;
        switch (sUriMatcher.match(uri)) {
            case BOOK_CODE:
                tableName = "book";
                break;
            case USER_CODE:
                tableName = "user";
                break;
            default:
                tableName = "book";
        }
        return tableName;
    }

    @Override
    public boolean onCreate() {
        mDb = new DbOpenHelper(getContext()).getWritableDatabase();
        mDb.execSQL("insert into book values(1,'android')");
        mDb.execSQL("insert into book values(2,'ios')");
        mDb.execSQL("insert into book values(3,'web')");


        mDb.execSQL("insert into user values(1,'andy',0)");
        mDb.execSQL("insert into user values(2,'jack',1)");
        mDb.execSQL("insert into user values(3,'rose',0)");
        return false;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String tableName = getTableName(uri);
        return mDb.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        String tableName = getTableName(uri);
        mDb.insert(tableName, null, contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

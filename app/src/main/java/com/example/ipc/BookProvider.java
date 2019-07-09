package com.example.ipc;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author lishibo
 * @date 2019-06-23
 * email : andy_li@swift365.com.cn
 */
public class BookProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        return false;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType( @NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert( @NonNull Uri uri,  @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete( @NonNull Uri uri,  @Nullable String s,  @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update( @NonNull Uri uri,  @Nullable ContentValues contentValues,  @Nullable String s,  @Nullable String[] strings) {
        return 0;
    }
}

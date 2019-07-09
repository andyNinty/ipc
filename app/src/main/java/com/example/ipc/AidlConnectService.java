package com.example.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lishibo
 * @date 2019-06-14
 * email : andy_li@swift365.com.cn
 */
public class AidlConnectService extends Service {

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "android"));
        mBookList.add(new Book(2, "ios"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBookManger.Stub mBinder = new IBookManger.Stub() {
        @Override
        public List<Book> getBooks() {
            return mBookList;
        }

        @Override
        public void addBook(Book book) {
            mBookList.add(book);
        }
    };
}

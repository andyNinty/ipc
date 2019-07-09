package com.example.ipc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private IBookManger iBookManger;
    private TextView textView;
    private Messenger messenger;
    final private Messenger replyMessenger =
            new Messenger(new ReplyMessenger(new WeakReference<Activity>(this)));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        new OpenHelper(getApplicationContext());
        Button btnBinder = findViewById(R.id.btn_binder);
        Button btnMessenger = findViewById(R.id.btn_messenger);

        textView = findViewById(R.id.textview);

        // 使用aidl文件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AidlConnectService.class);
                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
            }
        });
        btnMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MessengerService.class);
                bindService(intent, mMessengerConnection, BIND_AUTO_CREATE);
            }
        });
    }

    private ServiceConnection mMessengerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            messenger = new Messenger(iBinder);
            Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);
            msg.replyTo = replyMessenger;
            Bundle bundle = new Bundle();
            bundle.putString("msg", "hello");
            msg.setData(bundle);

            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iBookManger = IBookManger.Stub.asInterface(iBinder);
            Book book = new Book(2, "web");
            try {
                iBookManger.addBook(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            try {
                textView.setText(iBookManger.getBooks().toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private static class ReplyMessenger extends Handler {
        private WeakReference<Activity> weakReference;

        public ReplyMessenger(WeakReference<Activity> weakReference) {
            this.weakReference = weakReference;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessengerService.MSG_REPLY:
                    Toast.makeText(weakReference.get(), msg.getData().getString("reply"), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mMessengerConnection);
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}

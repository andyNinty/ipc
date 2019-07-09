package com.example.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * @author lishibo
 * @date 2019-06-17
 * email : andy_li@swift365.com.cn
 */
public class MessengerService extends Service {
    private static final String TAG = "MessengerService";
    public static final int MSG_SAY_HELLO = 1;
    public static final int MSG_REPLY = 2;
    final private Messenger mMessenger = new Messenger(new MessengerHandler(this));

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private static class MessengerHandler extends Handler {
        private WeakReference<Service> weakReference;

        public MessengerHandler(Service service) {
            this.weakReference = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Log.d(TAG, msg.getData().getString("msg"));

                    // reply to client
                    Messenger client = msg.replyTo;
                    Message replyMsg = Message.obtain(null, MSG_REPLY, 0, 0);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "reply hello");
                    replyMsg.setData(bundle);
                    try {
                        client.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }
}

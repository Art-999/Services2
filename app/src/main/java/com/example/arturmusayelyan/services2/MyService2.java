package com.example.arturmusayelyan.services2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

/**
 * Created by artur.musayelyan on 15/01/2018.
 */

public class MyService2 extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int time = intent.getIntExtra("timeKey", 0);

        new MyThread(startId, time).start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MyThread extends Thread {
        private int startId;
        private int time;

        public MyThread(int startId, int time) {
            this.startId = startId;
            this.time = time;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                Intent intent = new Intent(Main2Activity.BROADCAST_ACTION);
                intent.putExtra("resultKey", i);
                sendBroadcast(intent);
                SystemClock.sleep(time);
            }
            stopSelf(startId);
        }
    }
}

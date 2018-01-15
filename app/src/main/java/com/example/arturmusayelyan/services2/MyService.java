package com.example.arturmusayelyan.services2;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by artur.musayelyan on 15/01/2018.
 */

public class MyService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PendingIntent pendingIntent = intent.getParcelableExtra(MainActivity.KEY_SEND_PENDINGINTENT);
        int time = intent.getIntExtra(MainActivity.KEY_SEND_TIME, 0);

        new MyThread(time, startId, pendingIntent).start();
        //Log.d("Art",new MyThread(time, startId, pendingIntent).toString());
        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class MyThread extends Thread {
        private int time;
        private int startId;
        private PendingIntent pendingIntent;

        public MyThread(int time, int startId, PendingIntent pendingIntent) {
            this.time = time;
            this.startId = startId;
            this.pendingIntent = pendingIntent;
        }

        @Override
        public void run() {
            super.run();
            try {
                pendingIntent.send(MainActivity.KEY_STATUS_START);
                Log.d("Art", "worked");
                SystemClock.sleep(time);

                Intent intent = new Intent();
                for (int i = 1; i <= 10; i++) {
                    intent.putExtra("result", i);
                    pendingIntent.send(MyService.this, MainActivity.KEY_STATUS_WORKING, intent);
                    SystemClock.sleep(1000);
                }


                pendingIntent.send(MainActivity.KEY_STATUS_FINISH);

                SystemClock.sleep(2000);
                pendingIntent.send(MainActivity.KEY_STATUS_TASKFINISHED);
                stopSelf(startId);
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }
}

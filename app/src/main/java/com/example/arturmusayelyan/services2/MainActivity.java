package com.example.arturmusayelyan.services2;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ////PendingIntent Example Service. Обратная связь с помощью PendingIntent
    private Button startServiceBtn;
    private TextView showServiceResultTv1;
    private TextView showServiceResultTv2;
    private TextView showServiceResultTv3;
    private PendingIntent pendingIntent;
    private Intent intent;

    private final int TASK1_REQUESTCODE = 1;
    private final int TASK2_REQUESTCODE = 2;
    private final int TASK3_REQUESTCODE = 3;

    public final static String KEY_SEND_TIME = "time";
    public final static String KEY_SEND_PENDINGINTENT = "pendingIntent";
    public final static int KEY_STATUS_START = 100;
    public final static int KEY_STATUS_FINISH = 200;
    public final static int KEY_STATUS_WORKING = 300;
    public final static int KEY_STATUS_TASKFINISHED = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showServiceResultTv1 = findViewById(R.id.show_service_result_tv1);
        showServiceResultTv2 = findViewById(R.id.show_service_result_tv2);
        showServiceResultTv3 = findViewById(R.id.show_service_result_tv3);
        startServiceBtn = findViewById(R.id.start_service_btn);
    }

    public void startButtonClick(View view) {
        startServiceForTask1();
        startServiceForTask2();
        startServiceForTask3();
    }

    public void startServiceForTask1() {
        pendingIntent = createPendingResult(TASK1_REQUESTCODE, new Intent(), 0);
        intent = new Intent(this, MyService.class);
        intent.putExtra(KEY_SEND_TIME, 3000);
        intent.putExtra(KEY_SEND_PENDINGINTENT, pendingIntent);
        Log.d("Art", "startServiceForTask1() worked");
        startService(intent);
    }

    public void startServiceForTask2() {
        pendingIntent = createPendingResult(TASK2_REQUESTCODE, new Intent(), 0);
        intent = new Intent(this, MyService.class);
        intent.putExtra(KEY_SEND_TIME, 4000);
        intent.putExtra(KEY_SEND_PENDINGINTENT, pendingIntent);
        startService(intent);
    }

    public void startServiceForTask3() {
        pendingIntent = createPendingResult(TASK3_REQUESTCODE, new Intent(), 0);
        intent = new Intent(this, MyService.class);
        intent.putExtra(KEY_SEND_TIME, 5000);
        intent.putExtra(KEY_SEND_PENDINGINTENT, pendingIntent);
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == KEY_STATUS_START) {
            switch (requestCode) {
                case TASK1_REQUESTCODE:
                    showServiceResultTv1.setText(" Task 1 start...");
                    startServiceBtn.setEnabled(false);
                    break;
                case TASK2_REQUESTCODE:
                    showServiceResultTv2.setText(" Task 2 start...");
                    startServiceBtn.setEnabled(false);
                    break;
                case TASK3_REQUESTCODE:
                    showServiceResultTv3.setText(" Task 3 start...");
                    startServiceBtn.setEnabled(false);
                    break;
            }
        }

        if (resultCode == KEY_STATUS_WORKING) {
            int result = data.getIntExtra("result", 0);
            switch (requestCode) {
                case TASK1_REQUESTCODE:
                    showServiceResultTv1.setText(" Task 1 working progress: " + result);
                    break;
                case TASK2_REQUESTCODE:
                    showServiceResultTv2.setText(" Task 2 working progress: " + result);
                    break;
                case TASK3_REQUESTCODE:
                    showServiceResultTv3.setText(" Task 3 working progress: " + result);
                    break;
            }
        }

        if (resultCode == KEY_STATUS_FINISH) {
            switch (requestCode) {
                case TASK1_REQUESTCODE:
                    showServiceResultTv1.setText(" Task 1 finish:");
                    break;
                case TASK2_REQUESTCODE:
                    showServiceResultTv2.setText(" Task 2 finish:");
                    break;
                case TASK3_REQUESTCODE:
                    showServiceResultTv3.setText(" Task 3 finish:");
                    break;
            }
        }
        if (resultCode == KEY_STATUS_TASKFINISHED) {
            switch (requestCode) {
                case TASK1_REQUESTCODE:
                    showServiceResultTv1.setText("");
                   // startServiceBtn.setEnabled(true);
                    break;
                case TASK2_REQUESTCODE:
                    showServiceResultTv2.setText("");
                    //startServiceBtn.setEnabled(true);
                    break;
                case TASK3_REQUESTCODE:
                    showServiceResultTv3.setText("");
                    startServiceBtn.setEnabled(true);
                    break;
            }
        }
    }
}

package com.example.lenovo.countdowntimerdemo;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import static android.media.MediaExtractor.MetricsConstants.FORMAT;

public class MainActivity extends AppCompatActivity {
    public MySharedPreference prefManager;
    String sessiontimeinSecFromSh;
    private static final String FORMAT = "%02d:%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefManager = new MySharedPreference();

        Boolean b = prefManager.readBoolean(getApplicationContext(), "videosession", "ispassedtimepresent");

        if (b == true) {
            sessiontimeinSecFromSh = prefManager.readString(getApplicationContext(), "videosession", "sessiontime");
            Log.d("MYLOG", "MainActivity: " + "onCreate: " + "sessiontimeinSecFromSh: " + sessiontimeinSecFromSh);
        } else {
            sessiontimeinSecFromSh = "3600";
            Log.d("MYLOG", "MainActivity: " + "onCreate: " + "sessiontimeinSecFromSh: " + "no saved value");

        }

        final TextView textViewShowCount = findViewById(R.id.textViewShowCount);
        new CountDownTimer(1000 * Integer.parseInt(sessiontimeinSecFromSh), 1000) {
            public void onTick(long millisUntilFinished) {

                // textViewShowCount.setText(String.valueOf(millisUntilFinished / 1000));
                textViewShowCount.setText(String.format(FORMAT, TimeUnit.MILLISECONDS.toHours(millisUntilFinished), TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                Log.d("MYLOG", "MainActivity: " + "onTick: " + "millisUntilFinished: " + String.valueOf(millisUntilFinished / 1000));
                prefManager.writeString(getApplicationContext(), "videosession", "sessiontime", String.valueOf(millisUntilFinished / 1000));
                prefManager.writeBoolean(getApplicationContext(), "videosession", "ispassedtimepresent", true);

            }

            public void onFinish() {
                textViewShowCount.setText("FINISH!!");
            }
        }.start();
    }
}

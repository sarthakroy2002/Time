package com.neolit.time;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Stopwatch extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    private long startTime;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        handler = new Handler(Looper.getMainLooper());
        runTimer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
            startTime = System.currentTimeMillis() - (seconds * 1000L);
        }
    }

    public void onClickStart(View view) {
        if (!running) {
            running = true;
            startTime = System.currentTimeMillis() - (seconds * 1000L);
            runTimer();
        }
    }

    public void onClickStop(View view) {
        if (running) {
            running = false;
            handler.removeCallbacksAndMessages(null);
        }
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
        handler.removeCallbacksAndMessages(null);
        updateTimer();
    }

    private void runTimer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (running) {
                    long currentTime = System.currentTimeMillis();
                    seconds = (int) ((currentTime - startTime) / 1000);
                    updateTimer();
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    private void updateTimer() {
        final TextView timeView = findViewById(R.id.time_view);

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
        timeView.setText(time);
    }
}

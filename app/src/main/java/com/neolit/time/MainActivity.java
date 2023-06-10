package com.neolit.time;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.materialswitch.MaterialSwitch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mTime = findViewById(R.id.ttime);
        TextView mDate = findViewById(R.id.tdate);
        TextView mDay = findViewById(R.id.tday);
        TextView mTimezone = findViewById(R.id.ttimezone);

        MaterialSwitch mHoursSwitch = findViewById(R.id.hour_switch);
        MaterialSwitch mSecondsSwitch = findViewById(R.id.seconds_switch);

        AtomicReference<String> TimeFormat = new AtomicReference<>("h:mm a");

        mSecondsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                if(mHoursSwitch.isChecked()) {
                    TimeFormat.set("H:mm:ss");
                }else{
                    TimeFormat.set("h:mm:ss a");
                }
            }else{
                if(mHoursSwitch.isChecked()){
                    TimeFormat.set("H:mm");
                }else{
                    TimeFormat.set("h:mm a");
                }
            }
        });

        mHoursSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!isChecked) {
                if(mSecondsSwitch.isChecked()) {
                    TimeFormat.set("h:mm:ss a");
                }else{
                    TimeFormat.set("h:mm a");
                }
            }else{
                if(mSecondsSwitch.isChecked()) {
                    TimeFormat.set("H:mm:ss");
                }else{
                    TimeFormat.set("H:mm");
                }
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    DateFormat dfTime = new SimpleDateFormat(TimeFormat.get(), Locale.getDefault());
                    mTime.setText(dfTime.format(Calendar.getInstance().getTime()));

                    DateFormat dfDate = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
                    mDate.setText(dfDate.format(Calendar.getInstance().getTime()));

                    DateFormat dfDay = new SimpleDateFormat("EEEE", Locale.getDefault());
                    mDay.setText(dfDay.format(Calendar.getInstance().getTime()));

                    DateFormat dfTz = new SimpleDateFormat("zzzz", Locale.getDefault());
                    mTimezone.setText(dfTz.format(Calendar.getInstance().getTime()));

                });
            }
        }, 0, 1000);
    }
}
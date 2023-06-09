package com.neolit.time;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.materialswitch.MaterialSwitch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

        DateFormat dfTime = new SimpleDateFormat("h:mm a", Locale.getDefault());
        mTime.setText(dfTime.format(Calendar.getInstance().getTime()));

        DateFormat dfDate = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
        mDate.setText(dfDate.format(Calendar.getInstance().getTime()));

        DateFormat dfDay = new SimpleDateFormat("EEEE", Locale.getDefault());
        mDay.setText(dfDay.format(Calendar.getInstance().getTime()));

        DateFormat dfTz = new SimpleDateFormat("zzzz", Locale.getDefault());
        mTimezone.setText(dfTz.format(Calendar.getInstance().getTime()));

        final DateFormat[] dfsecTime = new DateFormat[1];
        final DateFormat[] df24Time = new DateFormat[1];

        mSecondsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                if(mHoursSwitch.isChecked()) {
                    dfsecTime[0] = new SimpleDateFormat("H:mm:ss a", Locale.getDefault());
                }else{
                    dfsecTime[0] = new SimpleDateFormat("h:mm:ss a", Locale.getDefault());
                }
            }else{
                if(mHoursSwitch.isChecked()){
                    dfsecTime[0] = new SimpleDateFormat("H:mm a", Locale.getDefault());
                }else{
                    dfsecTime[0] = new SimpleDateFormat("h:mm a", Locale.getDefault());
                }
            }
            mTime.setText(dfsecTime[0].format(Calendar.getInstance().getTime()));
        });

        mHoursSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!isChecked) {
                if(mSecondsSwitch.isChecked()) {
                    df24Time[0] = new SimpleDateFormat("h:mm:ss a", Locale.getDefault());
                }else{
                    df24Time[0] = new SimpleDateFormat("h:mm a", Locale.getDefault());
                }
            }else{
                if(mSecondsSwitch.isChecked()) {
                    df24Time[0] = new SimpleDateFormat("H:mm:ss a", Locale.getDefault());
                }else{
                    df24Time[0] = new SimpleDateFormat("H:mm a", Locale.getDefault());
                }
            }
            mTime.setText(df24Time[0].format(Calendar.getInstance().getTime()));
        });

    }
}
package com.example.tranquillo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduleMidnightAlarms();
        SBscheduleMidnightAlarms();
    }

    /**
     * this method is required to schedule alarms at midnight everyday
     * this doesn't disturb the user but works silently in the background to schedule next day's alarms
     */
    public void scheduleMidnightAlarms(){
        Intent intent=new Intent(MainActivity.this,NotificationBroadcastReceiver.class);
        intent.putExtra(NotificationBroadcastReceiver.ALARM_TYPE,NotificationBroadcastReceiver.DAY_SCHEDULER);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity.this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar night = Calendar.getInstance();
        night.add(Calendar.HOUR_OF_DAY, +24);
        night.set(Calendar.HOUR_OF_DAY, 0);
        night.set(Calendar.MINUTE, 1);
        night.set(Calendar.SECOND, 0);
        night.set(Calendar.MILLISECOND, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC, night.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void SBscheduleMidnightAlarms()
    {
        Intent intent = new Intent(MainActivity.this,SB_NotificationBReceiver.class);
        intent.putExtra(SB_NotificationBReceiver.ALARM_TYPE,SB_NotificationBReceiver.DAY_SCHEDULER);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar night = Calendar.getInstance();
        night.add(Calendar.HOUR_OF_DAY, +24);
        night.set(Calendar.HOUR_OF_DAY, 0);
        night.set(Calendar.MINUTE, 1);
        night.set(Calendar.SECOND, 0);
        night.set(Calendar.MILLISECOND, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC, night.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void nextPage(View view) {
        Intent btn = new Intent(MainActivity.this,WelcomePg.class);
        startActivity(btn);
        finish();
    }
}
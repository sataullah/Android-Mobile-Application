package com.example.tranquillo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.util.Calendar;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    public final static String ALARM_TYPE = "notificationTypeKey";
    public final static String DAY_SCHEDULER = "dayScheduler",
            REMINDER = "waterReminder";
    final static int interval = 3 * 60 * 60 * 1000; //3 hours in milliseconds

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            scheduleDay(context);
        } else if (intent.getStringExtra(ALARM_TYPE) != null) {
            switch (intent.getStringExtra(ALARM_TYPE)) {
                case DAY_SCHEDULER:
                    scheduleDay(context);
                    break;
                case REMINDER: {
                    publishNotification(context);
                    checkAndCancelFurtherAlarms(context);
                    break;
                }
            }
        }
    }

    /**
     * @param context This method may be called
     *                (1) at midnight everyday
     *                (2) when timings have recently changed
     */
    public static void scheduleDay(Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, NotificationBroadcastReceiver.class);
                intent.putExtra(ALARM_TYPE, REMINDER);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                WaterNotificationData data = new WaterNotificationData(context);

                Calendar startCalendar = Calendar.getInstance();
                int day = startCalendar.get(Calendar.DAY_OF_WEEK);
                int time = data.getDayStartTime(day);
                if (time == -1) {
                    //no alarm
                    alarmManager.cancel(pendingIntent);
                    return;
                }
                int minute = time % 100;
                int hour = time / 100;
                startCalendar.set(Calendar.MILLISECOND, 0);
                startCalendar.set(Calendar.SECOND, 0);
                startCalendar.set(Calendar.MINUTE, minute);
                startCalendar.set(Calendar.HOUR_OF_DAY, hour);

                Calendar endCalendar = Calendar.getInstance();
                time = data.getDayEndTime(day);
                minute = time % 100;
                hour = time / 100;
                endCalendar.set(Calendar.MILLISECOND, 0);
                endCalendar.set(Calendar.SECOND, 0);
                endCalendar.set(Calendar.MINUTE, minute);
                endCalendar.set(Calendar.HOUR_OF_DAY, hour);

                //For case when this method is running to update itself for a recent change in timings
                //ex : start from 09:30AM, but this method is called at 11:00AM then adding 3 hours to avoid
                //multiple simultaneous notifications
                while (System.currentTimeMillis() >= startCalendar.getTimeInMillis()) {
                    startCalendar.add(Calendar.MILLISECOND, interval);
                }

                //while adding multiple instances of 3 hours, possible errors :
                // (1) day has changed / next day has started
                // (2) the end time for day has passed
                // So checking these 2 conditions
                if (startCalendar.get(Calendar.DAY_OF_WEEK) != day
                        || startCalendar.getTimeInMillis() >= endCalendar.getTimeInMillis()) {
                    alarmManager.cancel(pendingIntent);
                } else {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startCalendar.getTimeInMillis(), interval, pendingIntent);
                }
            }
        }).start();
    }

    void publishNotification(Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String CHANNEL_ID = "waterNotification";

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                Notification.Builder builder = new Notification.Builder(context.getApplicationContext());
                builder.setContentTitle("Drink Water Notification");
                builder.setContentText("Keep Hydrated");
                builder.setSmallIcon(R.drawable.ic_baseline_notifications_24);
                builder.setGroup(CHANNEL_ID);
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Water Notifications", NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(channel);
                    builder.setChannelId(CHANNEL_ID);
                }

                Notification notification = builder.build();
                new Handler(Looper.getMainLooper())
                        .post(new Runnable() {
                            @Override
                            public void run() {
                                notificationManager.notify(0, notification);
                            }
                        });
            }
        }).start();
    }

    void checkAndCancelFurtherAlarms(Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, NotificationBroadcastReceiver.class);
                intent.putExtra(ALARM_TYPE, REMINDER);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                WaterNotificationData data = new WaterNotificationData(context);

                Calendar endCalendar = Calendar.getInstance();
                int day = endCalendar.get(Calendar.DAY_OF_WEEK);
                int time = data.getDayEndTime(day);
                int minute = time % 100;
                int hour = time / 100;
                endCalendar.set(Calendar.MILLISECOND, 0);
                endCalendar.set(Calendar.SECOND, 0);
                endCalendar.set(Calendar.MINUTE, minute);
                endCalendar.set(Calendar.HOUR_OF_DAY, hour);

            }
        }).start();
    }
}

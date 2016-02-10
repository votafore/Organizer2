package com.votafore.organizer.support;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.votafore.organizer.ActivityMain;

import java.util.Calendar;

public class ReceiverForAlarm extends BroadcastReceiver {

    public ReceiverForAlarm() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v("MyTest", "onReceive: отработали запуск расписания");

        Intent serviceIntent = new Intent(context, HandlingService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, ActivityMain.FIRST_SERVICE_ALARM_ID, serviceIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // время запуска будильника - 0:00
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 1);

        // запускаем наше расписание
        AlarmManager AlManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        AlManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
    }
}

package com.votafore.organizer.support;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.votafore.organizer.model.TaskManager;

import java.util.Calendar;
import java.util.List;

public class HandlingService extends IntentService{

    public HandlingService(){
        super("HandlingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.v("MyTest", "onHandleIntent: выполнение сервиса анализа задач");

        // получим список задач, для которых надо установить "будильник"
        List<TaskManager.Task> lList = TaskManager.getInstance(getApplicationContext()).getTodayTask();

        for(TaskManager.Task lTask: lList){

            // установим дату старта события
            Calendar cTask = Calendar.getInstance();
            cTask.setTimeInMillis(System.currentTimeMillis());
            cTask.set(Calendar.HOUR_OF_DAY  , lTask.getHour());
            cTask.set(Calendar.MINUTE       , lTask.getMinute());
            cTask.set(Calendar.SECOND       , 0);

            // устанавливаем "будильник" на запуск уведомления
            // точнее запускаем сервис, который выдаст окно с уведомлением
            // и начнет играть музыку (звуковое уведомление)
            Intent notifyService = new Intent(getApplicationContext(), NotifyService.class);
            notifyService.putExtra("ID", lTask.getId());
            PendingIntent alarmIntent = PendingIntent.getService(getApplicationContext(), lTask.getId(), notifyService, PendingIntent.FLAG_ONE_SHOT);

            // запускаем наше расписание
            AlarmManager AlManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            AlManager.set(AlarmManager.RTC_WAKEUP, cTask.getTimeInMillis(), alarmIntent);
        }
    }
}
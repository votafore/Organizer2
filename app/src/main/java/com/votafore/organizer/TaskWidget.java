package com.votafore.organizer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.votafore.organizer.active.ActivityDialogCurTaskList;
import com.votafore.organizer.model.TaskManager;

import java.util.List;


public class TaskWidget extends AppWidgetProvider {

    public static String UPDATE_WIDGETS = "com.votafore.organizer.widget.ACTION_WIDGET_RECEIVER";

    public void lUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // покажем количество задач на сегодня
        List<TaskManager.Task> mList = TaskManager.getInstance(context).getTodayTask();

        Intent listIntent = new Intent(context, ActivityDialogCurTaskList.class);

        PendingIntent pIntent = PendingIntent.getActivity(context, 0, listIntent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_task_widget);
        views.setTextViewText(R.id.notify, mList.size() == 0 ? "Задач нет" : "Есть задачи (" + mList.size() + ")");

        if(mList.size() != 0)
            views.setOnClickPendingIntent(R.id.notify, pIntent);

        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Log.i("MyTest", "Widget onReceive");

        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);

        lUpdate(context, appWidgetManager, ids);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Log.i("MyTest", "Widget onUpdate");

        lUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {

        super.onEnabled(context);

        Log.i("MyTest", "Widget onEnabled");

        Intent intent = new Intent(context, TaskWidget.class);
        intent.setAction(UPDATE_WIDGETS);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);
    }

    @Override
    public void onDisabled(Context context) {

        super.onDisabled(context);

        Log.i("MyTest", "onDisabled");

        Intent intent = new Intent(context, TaskWidget.class);
        intent.setAction(UPDATE_WIDGETS);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}


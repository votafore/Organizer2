package com.votafore.organizer.active;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.votafore.organizer.R;
import com.votafore.organizer.TaskWidget;
import com.votafore.organizer.model.ITaskData;
import com.votafore.organizer.model.TaskManager;
import com.votafore.organizer.support.HandlingService;

public class ActivityTask extends AppCompatActivity implements ITaskData {

    TaskManager.Task mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent i = getIntent();

        mTask = TaskManager.getInstance(this).getTask(i.getIntExtra("ID", 0));
    }

    @Override
    protected void onPause() {
        super.onPause();

        FragmentManager fManager = getSupportFragmentManager();
        Fragment f = fManager.findFragmentById(R.id.container_task);

        if(f != null)
            fManager.beginTransaction().remove(f).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        FragmentManager fManager = getSupportFragmentManager();
        fManager.beginTransaction().add(R.id.container_task, new FragmentTask()).commit();
    }


    ///////////////////////////////////////////
    // интерфейс (HOST)
    // для получения данных о задаче
    ///////////////////////////////////////////

    // title
    @Override
    public String getTaskTitle(){
        return mTask.getTitle();
    }

    @Override
    public void setTaskTitle(String newTitle) {
        mTask.setTitle(newTitle);
    }

    // description
    @Override
    public String getTaskDescription() {
        return mTask.getDescription();
    }

    @Override
    public void setTaskDescription(String newDescr) {
        mTask.setDescription(newDescr);
    }

    // date
    @Override
    public void setTaskDate(int year, int month, int day) {
        mTask.setDate(year, month, day);
    }

    @Override
    public long getTaskDate(){
        return mTask.getDate();
    }

    @Override
    public int getTaskYear() {
        return mTask.getYear();
    }

    @Override
    public int getTaskMonth() {
        return mTask.getMonth();
    }

    @Override
    public int getTaskDay() {
        return mTask.getDay();
    }

    // time
    @Override
    public void setTaskTime(int hour, int minute){
        mTask.setTime(hour, minute);
    }

    @Override
    public long getTaskTime(){
        return mTask.getTime();
    }

    @Override
    public int getTaskHour(){
        return mTask.getHour();
    }

    @Override
    public int getTaskMinute(){
        return mTask.getMinute();
    }

    // week day
    @Override
    public int getTaskDayOfWeek(int day){
        return mTask.getDayOfWeek(day);
    }

    @Override
    public void setTaskDayOfWeek(int day, int val) {
        mTask.setDayOfWeek(day, val);
    }

    // month
    @Override
    public int getTaskMonth(int month){
        return mTask.getMonths(month);
    }

    @Override
    public void setTaskMonth(int month, int val){
        mTask.setMonth(month, val);
    }




    @Override
    public void saveTask() {
        TaskManager.getInstance(this).saveTask(mTask);

        // обновляем виджет
        Intent widgetIntent = new Intent();
        widgetIntent.setAction(TaskWidget.UPDATE_WIDGETS);
        sendBroadcast(widgetIntent);

        // запускаем сервис проверки
        // вдруг задача должна выполниться сегодня
        Intent serviceIntent = new Intent(getApplicationContext(), HandlingService.class);
        startService(serviceIntent);

    }
}
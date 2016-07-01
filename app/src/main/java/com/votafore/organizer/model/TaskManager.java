package com.votafore.organizer.model;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.votafore.organizer.ActivityAlarm;
import com.votafore.organizer.ActivityMain;
import com.votafore.organizer.support.AdapterTaskList;
import com.votafore.organizer.support.DatabaseHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskManager {

    /**
     *  СТАНДАРНТНЫЙ РАЗДЕЛ (КОНСТРУКТОРЫ И ДР. ФУНКЦИИ И ПЕРЕМЕННЫЕ ПЕРВОЙ НЕОБХОДИМОСТИ)
     */

    private static TaskManager mThis;
    private Context mContext;

    private TaskManager(Context ctx){

        mContext = ctx;

        // получим задачи из базы
        db = new DatabaseHandler(mContext);
        mTaskList = db.getAllTask();
    }

    public static TaskManager getInstance(Context mContext){

        if(mThis == null){
            mThis = new TaskManager(mContext);
        }

        return mThis;
    }






    /**
     * раздел управления адаптером основного списка
     */

    private AdapterTaskList mListAdapter;

    public AdapterTaskList getListAdapter() {
        return mListAdapter;
    }

    public void setListListener(AdapterTaskList.IListener listener){
        mListAdapter.setListener(listener);
    }

    public void createAdapter(){
        mListAdapter = new AdapterTaskList(this);
    }







    /**
     * раздел по работе с базой данных
     */

    private DatabaseHandler db;

    public void saveTask(Task mTask){

        if(mTask.getId() == 0) {
            db.addTask(mTask);
            mTaskList.add(mTask);
            mListAdapter.notifyItemInserted(mTaskList.indexOf(mTask));
        }else{
            db.updateTask(mTask);
            mListAdapter.notifyItemChanged(mTaskList.indexOf(mTask));
        }

        // установим "будильник" для запуска уведомления
        List<Task> todayTask = getTodayTask();

        // проверим попадает ли текущая задача в сегодняшние
        // т.е. которые еще должны выполниться
        if(todayTask.indexOf(mTask) == -1)
            return;

        PendingIntent pIntent = getPIntent(mTask);

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY  , mTask.getHour());
        c.set(Calendar.MINUTE       , mTask.getMinute());
        c.set(Calendar.SECOND       , 0);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pIntent);
    }

    public void deleteTask(Task task){

        db.deleteTask(task);

        int position = mTaskList.indexOf(task);
        mTaskList.remove(position);

        mListAdapter.notifyItemMoved(position, position);

        PendingIntent pIntent = getPIntent(task);
        pIntent.cancel();
    }

    public PendingIntent getPIntent(Task mTask){

        Intent alarmI = new Intent(mContext, ActivityAlarm.class);
        alarmI.setAction("notify");
        alarmI.putExtra(ActivityMain.TASK_ID, mTask.getId());

        return PendingIntent.getActivity(mContext, mTask.getId(), alarmI, PendingIntent.FLAG_ONE_SHOT);
    }





    /**
     * управление задачей(задачами)
     */

    private List<Task>      mTaskList;

    public List<Task> getTaskList(){
        return mTaskList;
    }

    public Task getTask(int ID){

        Task result = new Task();

        for(int i=0;i<mTaskList.size();i++){

            if(ID == mTaskList.get(i).getId())
                result = mTaskList.get(i);
        }

        return result;
    }

    public List<Task> getTodayTask(){

        List<Task> result = new ArrayList<>();

        for(Task lTask: mTaskList){

            //////////////////////////////////
            // делаем проверку должна ли задача выполнятся сегодня

            // проверка даты старта

            // установим время на начало суток текущего дня
            Calendar cur = Calendar.getInstance();
            cur.setTimeInMillis(System.currentTimeMillis());
            cur.set(Calendar.HOUR_OF_DAY , 0);
            cur.set(Calendar.MINUTE      , 0);
            cur.set(Calendar.SECOND      , 0);

            // установим дату старта события
            Calendar cTask = Calendar.getInstance();
            cTask.set(Calendar.YEAR         , lTask.getYear() + 1900);
            cTask.set(Calendar.MONTH        , lTask.getMonth());
            cTask.set(Calendar.DAY_OF_MONTH , lTask.getDay());

            if(cur.getTimeInMillis() < cTask.getTimeInMillis())
                // дата старта еще не наступила
                continue;

            // проверка: прошло ли время события

            cur.setTimeInMillis(System.currentTimeMillis());

            cTask.setTimeInMillis(System.currentTimeMillis());
            cTask.set(Calendar.HOUR_OF_DAY  , lTask.getHour());
            cTask.set(Calendar.MINUTE       , lTask.getMinute());
            cTask.set(Calendar.SECOND       , 0);

            if(cur.getTimeInMillis() > cTask.getTimeInMillis())
                // время события уже прошло
                continue;

            // проверка месяца
            if(lTask.getMonths(cur.get(Calendar.MONTH) + 1) != 1)
                // задача не выполняется в текущем месяце
                continue;

            // проверка дня недели
            int day = cur.get(Calendar.DAY_OF_WEEK)-1;
            if(day == 0)
                day = 7;

            if(lTask.getDayOfWeek(day) != 1)
                // задача не выполняется в текущий день недели
                continue;

            // если мы тут, значит задача, все таки, должна выполниться сегодня
            result.add(lTask);
        }

        return result;
    }

}

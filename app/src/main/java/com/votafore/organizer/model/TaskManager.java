package com.votafore.organizer.model;


import android.content.Context;

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

        mListAdapter = new AdapterTaskList(this);

        // получим задачи из базы
        db = new DatabaseHandler(mContext);
        mTaskList = db.getAllTask();

//        mTaskList = new ArrayList<>();
//
//        Task task1, task2, task3, task4, task5;
//
//        task1 = new Task();
//        task1.setId(1);
//        task1.setTitle("test 1");
//        task1.setDate(2016,6,10);
//        task1.setTime(12,0);
//
//        task2 = new Task();
//        task2.setId(2);
//        task2.setTitle("test 2");
//        task2.setDate(2016,6,15);
//        task2.setTime(15,0);
//
//        task3 = new Task();
//        task3.setId(3);
//        task3.setTitle("test 3");
//        task3.setDate(2016,6,18);
//        task3.setTime(18,0);
//
//        task4 = new Task();
//        task4.setId(4);
//        task4.setTitle("test 4");
//        task4.setDate(2016,6,18);
//        task4.setTime(20,0);
//
//        task5 = new Task();
//        task5.setId(5);
//        task5.setTitle("test 5");
//        task5.setDate(2016,6,18);
//        task5.setTime(22,0);
//
//        mTaskList.add(task1);
//        mTaskList.add(task2);
//        mTaskList.add(task3);
//        mTaskList.add(task4);
//        mTaskList.add(task5);
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
    }

    public void deleteTask(Task task){

        db.deleteTask(task);

        int position = mTaskList.indexOf(task);
        mTaskList.remove(position);

        mListAdapter.notifyItemMoved(position, position);
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

package com.votafore.organizer.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskManager {

    private static TaskManager mThis;

    private List<Task> mTaskList;
   //private DatabaseHandler db;

    private TaskManager(Context ctx){

        // получим задачи из базы
        //db = new DatabaseHandler(ctx);
        //mTaskList = db.getAllTask();

        mTaskList = new ArrayList<>();

        Task task1, task2, task3, task4, task5;

        task1 = new Task();
        task1.setId(1);
        task1.setTitle("test 1");
        task1.setDate(2016,6,10);
        task1.setTime(12,0);

        task2 = new Task();
        task2.setId(2);
        task2.setTitle("test 2");
        task2.setDate(2016,6,15);
        task2.setTime(15,0);

        task3 = new Task();
        task3.setId(3);
        task3.setTitle("test 3");
        task3.setDate(2016,6,18);
        task3.setTime(18,0);

        task4 = new Task();
        task4.setId(4);
        task4.setTitle("test 4");
        task4.setDate(2016,6,18);
        task4.setTime(20,0);

        task5 = new Task();
        task5.setId(5);
        task5.setTitle("test 5");
        task5.setDate(2016,6,18);
        task5.setTime(22,0);

        mTaskList.add(task1);
        mTaskList.add(task2);
        mTaskList.add(task3);
        mTaskList.add(task4);
        mTaskList.add(task5);
    }

    public static TaskManager getInstance(Context mContext){

        if(mThis == null){
            mThis = new TaskManager(mContext);
        }

        return mThis;
    }

    public List<TaskManager.Task> getTaskList(){
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

    public void createNewTask(){

//        Task t = new Task();
//        db.addTask(t);
//
//        // сразу же обновим список
//        mTaskList = db.getAllTask();
    }

    public void saveTask(Task mTask){

        //db.updateTask(mTask);
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



    // класс, экземпляры которого хранят данные каждой задачи
    public static class Task{

        private int id;
        private String title;                   // заголовок события
        private String description;             // описание события

        private Date mStartDate;                // дата, с которой событие запущено (или будет запущено)
        private Date mTime;                     // время события

        //////////////////////////////
        // варианты 1 (данные цикла)
        private int loop_day;                   // частота повтора события (в днях)
        private int loop_week;                  // частота повтора события (в неделях)

        //////////////////////////////
        // варианты 2 (конкретные значения)
        private int[] day_of_week;              // дни недели по которым должно срабатывать уведомление
        private int[] months;                   // месяцы по которым должно срабатывать уведомление

        public Task(){

            this.id             = 0;
            this.title          = "";
            this.description    = "";

            this.day_of_week    = new int[]{1,1,1,1,1,1,1};
            this.months         = new int[]{1,1,1,1,1,1,1,1,1,1,1,1};

            this.mStartDate = new Date();
            this.mTime      = new Date();

            this.loop_day   = 1;
            this.loop_week  = 1;
        }

        ///////////////////////////////
        // ID
        public void setId(int id){
            this.id = id;
        }

        public int getId(){
            return this.id;
        }

        ///////////////////////////////
        // TITLE
        public void setTitle(String title){
            this.title = title;
        }

        public String getTitle(){
            return this.title;
        }

        ///////////////////////////////
        // DESCRIPTION
        public void setDescription(String description){
            this.description = description;
        }

        public String getDescription(){
            return this.description;
        }

        ///////////////////////////////
        // TIME
        public void setTime(int hour, int minute){

            this.mTime.setHours(hour);
            this.mTime.setMinutes(minute);
            this.mTime.setSeconds(0);
        }

        public int getHour(){
            return this.mTime.getHours();
        }

        public int getMinute(){
            return this.mTime.getMinutes();
        }

        public long getTime(){
            return this.mTime.getTime();
        }

        ///////////////////////////////
        // DATE
        public void setDate(int year, int month, int day){

            this.mStartDate.setYear(year);
            this.mStartDate.setMonth(month);
            this.mStartDate.setDate(day);
        }

        public void setDate(long date){
            this.mStartDate.setTime(date);
        }

        public long getDate(){
            return this.mStartDate.getTime();
        }

        public int getYear(){
            return this.mStartDate.getYear();
        }

        public int getMonth(){
            return this.mStartDate.getMonth();
        }

        public int getDay(){
            return this.mStartDate.getDate();
        }

        ///////////////////////////////
        // DAY OF WEEK
        public void setDayOfWeek(int day, int value){
            this.day_of_week[day-1] = value;
        }

        public int getDayOfWeek(int day){
            return this.day_of_week[day-1];
        }

        public void setDayOfWeekString(String days){

            for (int i=0; i<days.length(); i++){
                if(days.substring(i, (i+1)).equals("1")){
                    this.day_of_week[i] = 1;
                }else{
                    this.day_of_week[i] = 0;
                }
            }
        }

        public String getDayOfWeekString() {

            String result = "";

            for (int i = 0; i<7; i++){
                result = result + String.valueOf(this.day_of_week[i]);
            }

            return result;
        }

        ///////////////////////////////
        // MONTHS
        public void setMonth(int month, int value){
            this.months[month-1] = value;
        }

        public int getMonths(int index){
            return this.months[index-1];
        }

        public void setMonthsString(String months){

            for (int i=0; i<months.length(); i++){
                if(months.substring(i, (i+1)).equals("1")){
                    this.months[i] = 1;
                }else{
                    this.months[i] = 0;
                }
            }
        }

        public String getMonthsString() {

            String result = "";

            for (int i = 0; i<12; i++){
                result = result + String.valueOf(this.months[i]);
            }

            return result;
        }

        ///////////////////////////////
        // LOOP DAY
        public void setLoopDay(int loop){
            this.loop_day = loop;
        }

        public int getLoopDay(){
            return this.loop_day;
        }

        ///////////////////////////////
        // LOOP WEEK
        public void setLoopWeek(int loop){
            this.loop_week = loop;
        }

        public int getLoopWeek(){
            return this.loop_week;
        }
    }

    // класс для чтения\записи данных задач в базу данных
    public class DatabaseHandler extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION   = 7;
        private static final String DATABASE_NAME   = "TaskBase.db";
        private static final String TABLE_TASK      = "task";

        // имена колонок таблицы
        private static final String ID              = "id";
        private static final String TITLE           = "title";
        private static final String DESCRIPTION     = "description";
        private static final String MINUTE          = "minute";
        private static final String HOUR            = "hour";
        private static final String START_DATE      = "start_date";
        private static final String LOOP_DAY        = "loop_day";
        private static final String LOOP_WEEK       = "loop_week";
        private static final String DAYS_OF_WEEK    = "days_of_week";
        private static final String MONTHS          = "months";

        public DatabaseHandler(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TITLE + " TEXT, "
                    + DESCRIPTION + " TEXT, "
                    + MINUTE + " INTEGER, "
                    + HOUR + " INTEGER, "
                    + START_DATE + " NUMERIC, "
                    + LOOP_DAY + " INTEGER, "
                    + LOOP_WEEK + " INTEGER, "
                    + DAYS_OF_WEEK + " TEXT, "
                    + MONTHS + " TEXT)";

            db.execSQL(CREATE_TASK_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);

            onCreate(db);
        }

        public void addTask(Task lTask){

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(TITLE, lTask.getTitle());
            values.put(DESCRIPTION, lTask.getDescription());
            values.put(MINUTE, lTask.getMinute());
            values.put(HOUR, lTask.getHour());
            values.put(START_DATE, lTask.getDate());
            values.put(LOOP_DAY, lTask.getLoopDay());
            values.put(LOOP_WEEK, lTask.getLoopWeek());
            values.put(DAYS_OF_WEEK, lTask.getDayOfWeekString());
            values.put(MONTHS, lTask.getMonthsString());

            db.insert(TABLE_TASK, null, values);
            db.close();
        }

        public List<Task> getAllTask(){

            List<Task> taskList = new ArrayList<>();

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TASK, null);

            if (cursor.moveToFirst()){
                do{
                    Task task = new Task();
                    task.setId(cursor.getInt(0));
                    task.setTitle(cursor.getString(1));
                    task.setDescription(cursor.getString(2));
                    task.setTime(cursor.getInt(4), cursor.getInt(3));
                    task.setDate(cursor.getLong(5));
                    task.setLoopDay(cursor.getInt(6));
                    task.setLoopWeek(cursor.getInt(7));
                    task.setDayOfWeekString(cursor.getString(8));
                    task.setMonthsString(cursor.getString(9));

                    taskList.add(task);
                }while (cursor.moveToNext());
            }
            return taskList;
        }

        public int updateTask(Task lTask){

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ID, lTask.getId());
            values.put(TITLE, lTask.getTitle());
            values.put(DESCRIPTION, lTask.getDescription());
            values.put(MINUTE, lTask.getMinute());
            values.put(HOUR, lTask.getHour());
            values.put(START_DATE, lTask.getDate());
            values.put(LOOP_DAY, lTask.getLoopDay());
            values.put(LOOP_WEEK, lTask.getLoopWeek());
            values.put(DAYS_OF_WEEK, lTask.getDayOfWeekString());
            values.put(MONTHS, lTask.getMonthsString());

            return db.update(TABLE_TASK, values, ID + "=?", new String[]{String.valueOf(lTask.getId())});
        }

        public void deleteTask(Task lTask){

            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_TASK, ID + "=?", new String[]{String.valueOf(lTask.getId())});

            db.close();
        }
    }

}

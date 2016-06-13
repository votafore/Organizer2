package com.votafore.organizer.support;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.votafore.organizer.model.Task;

import java.util.ArrayList;
import java.util.List;

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

        long rowID;

        rowID = db.insert(TABLE_TASK, null, values);
        db.close();

        lTask.setId((int)rowID);
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

package com.votafore.organizer.model;

import android.net.Uri;

import java.util.Date;

public class Task implements Cloneable{


    private int id;
    private String title;                   // заголовок события
    private String description;             // описание события

    private Uri mSignalRef;                 // ссылка на мелодию уведомления

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

    public Task() {

        this.id = 0;
        this.title = "";
        this.description = "";

        this.day_of_week = new int[]{1, 1, 1, 1, 1, 1, 1};
        this.months = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

        this.mStartDate = new Date();
        this.mTime = new Date();

        this.loop_day = 1;
        this.loop_week = 1;
    }

    public int getId() {
        return this.id;
    }

    ///////////////////////////////
    // ID
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    ///////////////////////////////
    // TITLE
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    ///////////////////////////////
    // DESCRIPTION
    public void setDescription(String description) {
        this.description = description;
    }

    ///////////////////////////////
    // TIME
    public void setTime(int hour, int minute) {

        this.mTime.setHours(hour);
        this.mTime.setMinutes(minute);
        this.mTime.setSeconds(0);
    }

    public int getHour() {
        return this.mTime.getHours();
    }

    public int getMinute() {
        return this.mTime.getMinutes();
    }

    public long getTime() {
        return this.mTime.getTime();
    }

    ///////////////////////////////
    // DATE
    public void setDate(int year, int month, int day) {

        this.mStartDate.setYear(year);
        this.mStartDate.setMonth(month);
        this.mStartDate.setDate(day);
    }

    public long getDate() {
        return this.mStartDate.getTime();
    }

    public void setDate(long date) {
        this.mStartDate.setTime(date);
    }

    public int getYear() {
        return this.mStartDate.getYear();
    }

    public int getMonth() {
        return this.mStartDate.getMonth();
    }

    public int getDay() {
        return this.mStartDate.getDate();
    }

    ///////////////////////////////
    // DAY OF WEEK
    public void setDayOfWeek(int day, int value) {
        this.day_of_week[day - 1] = value;
    }

    public int getDayOfWeek(int day) {
        return this.day_of_week[day - 1];
    }

    public String getDayOfWeekString() {

        String result = "";

        for (int i = 0; i < 7; i++) {
            result = result + String.valueOf(this.day_of_week[i]);
        }

        return result;
    }

    public void setDayOfWeekString(String days) {

        for (int i = 0; i < days.length(); i++) {
            if (days.substring(i, (i + 1)).equals("1")) {
                this.day_of_week[i] = 1;
            } else {
                this.day_of_week[i] = 0;
            }
        }
    }

    ///////////////////////////////
    // MONTHS
    public void setMonth(int month, int value) {
        this.months[month - 1] = value;
    }

    public int getMonths(int index) {
        return this.months[index - 1];
    }

    public String getMonthsString() {

        String result = "";

        for (int i = 0; i < 12; i++) {
            result = result + String.valueOf(this.months[i]);
        }

        return result;
    }

    public void setMonthsString(String months) {

        for (int i = 0; i < months.length(); i++) {
            if (months.substring(i, (i + 1)).equals("1")) {
                this.months[i] = 1;
            } else {
                this.months[i] = 0;
            }
        }
    }

    public int getLoopDay() {
        return this.loop_day;
    }

    ///////////////////////////////
    // LOOP DAY
    public void setLoopDay(int loop) {
        this.loop_day = loop;
    }


    ///////////////////////////////
    // LOOP WEEK
    public void setLoopWeek(int loop) {
        this.loop_week = loop;
    }

    public int getLoopWeek() {
        return this.loop_week;
    }

    //////////////////////////////
    // SIGNAL
    public Uri getSignalRef() {
        return mSignalRef;
    }

    public void setSignalRef(Uri ref) {
        mSignalRef = ref;
    }




    /////////////////////////////
    // CLONEABLE

    public Task getClone(){

        try {
            return (Task)clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return new Task();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        Task t = new Task();

        t.setId(id);
        t.setTitle(title);
        t.setDescription(description);
        t.setSignalRef(mSignalRef);
        t.setDate(getYear(),getMonth(), getDay());
        t.setTime(getHour(), getMinute());
        t.setDayOfWeekString(getDayOfWeekString());
        t.setMonthsString(getMonthsString());
        t.setLoopDay(loop_day);
        t.setLoopWeek(loop_week);

        return t;
    }
}


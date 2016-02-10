package com.votafore.organizer.active;


public interface ITaskData {

    int getTaskMonth(int month);
    void setTaskMonth(int month, int val);

    int getTaskDayOfWeek(int day);
    void setTaskDayOfWeek(int day, int val);

    String getTaskTitle();
    void setTaskTitle(String newTitle);

    String getTaskDescription();
    void setTaskDescription(String newDescr);

    void setTaskDate(int year, int month, int day);
    long getTaskDate();
    int getTaskYear();
    int getTaskMonth();
    int getTaskDay();


    void setTaskTime(int hour, int minute);
    long getTaskTime();
    int getTaskHour();
    int getTaskMinute();


    void saveTask();
}

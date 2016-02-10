package com.votafore.organizer.active;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.votafore.organizer.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FragmentTask extends Fragment{

    ViewPager mViewPager;

    ITaskData ITask;

    CharSequence[] mTitles = {"Настройки по дням","Настройки по дням недели","Настройки по месяцам"};

    CustomTabHost mSlidingTabLayout;

    private List<TabItem> mTabs = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTabs.add(new TabItem(
                0,
                Color.BLUE, // Indicator color
                Color.GRAY // Divider color
        ));

        mTabs.add(new TabItem(
                1,
                Color.RED, // Indicator color
                Color.GRAY // Divider color
        ));

        mTabs.add(new TabItem(
                2,
                Color.YELLOW, // Indicator color
                Color.GRAY // Divider color
        ));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ITask = (ITaskData)getActivity();

        View v = inflater.inflate(R.layout.fragment_task, null);

        final Button btnTitle        = (Button)v.findViewById(R.id.task_item_title);
        final Button btnDescription  = (Button)v.findViewById(R.id.task_item_description);
        final Button btnTaskTime     = (Button)v.findViewById(R.id.task_time);
        final Button btnTaskDate     = (Button)v.findViewById(R.id.task_date);

        // установка существующих значений
        btnTitle        .setText(ITask.getTaskTitle());
        btnDescription  .setText(ITask.getTaskDescription());
        btnTaskTime     .setText(SimpleDateFormat.getTimeInstance(SimpleDateFormat.DEFAULT, Locale.UK).format(new Date(ITask.getTaskTime())));
        btnTaskDate     .setText(SimpleDateFormat.getDateInstance(SimpleDateFormat.DEFAULT, Locale.UK).format(new Date(ITask.getTaskDate())));

        // обработчики для изменения
        btnTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater lInflater = LayoutInflater.from(getActivity());
                View dialogView = lInflater.inflate(R.layout.dialog_input_text, null);

                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getActivity());
                mDialogBuilder.setView(dialogView);

                final EditText lInputText = (EditText) dialogView.findViewById(R.id.editing_text);
                lInputText.setText(btnTitle.getText());

                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                btnTitle.setText(lInputText.getText());

                                // устанавливаем заголовок в задаче и сохраняем ее
                                ITask.setTaskTitle(lInputText.getText().toString());
                                ITask.saveTask();
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.show();
            }
        });

        btnDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater lInflater = LayoutInflater.from(getActivity());
                View dialogView = lInflater.inflate(R.layout.dialog_input_text, null);

                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getActivity());
                mDialogBuilder.setView(dialogView);

                final EditText lInputText = (EditText) dialogView.findViewById(R.id.editing_text);
                lInputText.setText(btnDescription.getText());

                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                btnDescription.setText(lInputText.getText());

                                // устанавливаем заголовок в задаче и сохраняем ее
                                ITask.setTaskDescription(lInputText.getText().toString());
                                ITask.saveTask();
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.show();
            }
        });

        btnTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog lDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        ITask.setTaskDate(year - 1900, monthOfYear, dayOfMonth);

                        btnTaskDate.setText(SimpleDateFormat.getDateInstance(SimpleDateFormat.DEFAULT, Locale.UK).format(new Date(ITask.getTaskDate())));

                        // запись изменений
                        ITask.saveTask();
                    }
                }, ITask.getTaskYear() + 1900, ITask.getTaskMonth(), ITask.getTaskDay());

                lDialog.show();
            }
        });

        btnTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        ITask.setTaskTime(hourOfDay, minute);

                        btnTaskTime.setText(SimpleDateFormat.getTimeInstance(SimpleDateFormat.DEFAULT, Locale.UK).format(new Date(ITask.getTaskTime())));

                        // запись изменений
                        ITask.saveTask();
                    }
                }, ITask.getTaskHour(), ITask.getTaskMinute(), true);

                dialog.show();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = (ViewPager)view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new AdapterViewPager(getChildFragmentManager()));

        mSlidingTabLayout = (CustomTabHost) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);

        mSlidingTabLayout.setCustomTabColorizer(new CustomTabHost.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return mTabs.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return mTabs.get(position).getDividerColor();
            }

        });
    }







    public class AdapterViewPager extends FragmentPagerAdapter {

        public  AdapterViewPager(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mTabs.get(position).createFragment();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }


    public class TabItem{

        private final int mIndex;
        private final int mIndicatorColor;
        private final int mDividerColor;

        public TabItem(int  lIndex, int indicatorColor, int dividerColor) {
            this.mIndex = lIndex;
            this.mIndicatorColor = indicatorColor;
            this.mDividerColor = dividerColor;
        }

        Fragment createFragment() {

            Fragment f = new FragmentPageDaySettings();

            switch(this.mIndex){
                case 1:
                    f = new FragmentPageWeekDaySettings();
                    break;
                case 2:
                    f = new FragmentPageMonthSettings();
            }

            return f;
        }

        CharSequence getTitle() {
            return mTitles[this.mIndex];
        }

        int getIndicatorColor() {
            return mIndicatorColor;
        }

        int getDividerColor() {
            return mDividerColor;
        }
    }
}

package com.votafore.organizer.active.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.votafore.organizer.R;
import com.votafore.organizer.model.ITaskData;

public class PageSettingsDays extends Fragment {

    ITaskData ITask;

    public static PageSettingsDays getInstance(ITaskData i_data) {

        Bundle args = new Bundle();
        PageSettingsDays f = new PageSettingsDays();

        f.setArguments(args);
        f.setInterface(i_data);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_day_settings, container, false);

        WeekDayListener mDayListener = new WeekDayListener();
        MonthsListener mMonthsListener = new MonthsListener();

        // настройки дней недели
        TextView btn_mnd = (TextView) v.findViewById(R.id.btn_mnd);
        TextView btn_tsd = (TextView) v.findViewById(R.id.btn_tsd);
        TextView btn_wnd = (TextView) v.findViewById(R.id.btn_wnd);
        TextView btn_thd = (TextView) v.findViewById(R.id.btn_thd);
        TextView btn_frd = (TextView) v.findViewById(R.id.btn_frd);
        TextView btn_std = (TextView) v.findViewById(R.id.btn_std);
        TextView btn_snd = (TextView) v.findViewById(R.id.btn_snd);

        setBkgColor(btn_mnd, ITask.getTaskDayOfWeek(1));
        setBkgColor(btn_tsd, ITask.getTaskDayOfWeek(2));
        setBkgColor(btn_wnd, ITask.getTaskDayOfWeek(3));
        setBkgColor(btn_thd, ITask.getTaskDayOfWeek(4));
        setBkgColor(btn_frd, ITask.getTaskDayOfWeek(5));
        setBkgColor(btn_std, ITask.getTaskDayOfWeek(6));
        setBkgColor(btn_snd, ITask.getTaskDayOfWeek(7));

        btn_mnd.setOnClickListener(mDayListener);
        btn_tsd.setOnClickListener(mDayListener);
        btn_wnd.setOnClickListener(mDayListener);
        btn_thd.setOnClickListener(mDayListener);
        btn_frd.setOnClickListener(mDayListener);
        btn_std.setOnClickListener(mDayListener);
        btn_snd.setOnClickListener(mDayListener);


        // настройки месяцев
        TextView btn_jan = (TextView) v.findViewById(R.id.btn_jan);
        TextView btn_fab = (TextView) v.findViewById(R.id.btn_fab);
        TextView btn_mch = (TextView) v.findViewById(R.id.btn_mch);
        TextView btn_apr = (TextView) v.findViewById(R.id.btn_apr);
        TextView btn_may = (TextView) v.findViewById(R.id.btn_may);
        TextView btn_jun = (TextView) v.findViewById(R.id.btn_jun);
        TextView btn_jul = (TextView) v.findViewById(R.id.btn_jul);
        TextView btn_aug = (TextView) v.findViewById(R.id.btn_aug);
        TextView btn_sep = (TextView) v.findViewById(R.id.btn_sep);
        TextView btn_oct = (TextView) v.findViewById(R.id.btn_oct);
        TextView btn_nov = (TextView) v.findViewById(R.id.btn_nov);
        TextView btn_dec = (TextView) v.findViewById(R.id.btn_dec);

        setBkgColor(btn_jan, ITask.getTaskMonth(1));
        setBkgColor(btn_fab, ITask.getTaskMonth(2));
        setBkgColor(btn_mch, ITask.getTaskMonth(3));
        setBkgColor(btn_apr, ITask.getTaskMonth(4));
        setBkgColor(btn_may, ITask.getTaskMonth(5));
        setBkgColor(btn_jun, ITask.getTaskMonth(6));
        setBkgColor(btn_jul, ITask.getTaskMonth(7));
        setBkgColor(btn_aug, ITask.getTaskMonth(8));
        setBkgColor(btn_sep, ITask.getTaskMonth(9));
        setBkgColor(btn_oct, ITask.getTaskMonth(10));
        setBkgColor(btn_nov, ITask.getTaskMonth(11));
        setBkgColor(btn_dec, ITask.getTaskMonth(12));

        btn_jan.setOnClickListener(mMonthsListener);
        btn_fab.setOnClickListener(mMonthsListener);
        btn_mch.setOnClickListener(mMonthsListener);
        btn_apr.setOnClickListener(mMonthsListener);
        btn_may.setOnClickListener(mMonthsListener);
        btn_jun.setOnClickListener(mMonthsListener);
        btn_jul.setOnClickListener(mMonthsListener);
        btn_aug.setOnClickListener(mMonthsListener);
        btn_sep.setOnClickListener(mMonthsListener);
        btn_oct.setOnClickListener(mMonthsListener);
        btn_nov.setOnClickListener(mMonthsListener);
        btn_dec.setOnClickListener(mMonthsListener);

        return v;
    }

    public void setInterface(ITaskData i_data) {
        ITask = i_data;
    }


    public class WeekDayListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int day = 0;

            switch (v.getId()) {
                case R.id.btn_mnd:
                    day = 1;
                    break;
                case R.id.btn_tsd:
                    day = 2;
                    break;
                case R.id.btn_wnd:
                    day = 3;
                    break;
                case R.id.btn_thd:
                    day = 4;
                    break;
                case R.id.btn_frd:
                    day = 5;
                    break;
                case R.id.btn_std:
                    day = 6;
                    break;
                case R.id.btn_snd:
                    day = 7;
            }

            int val = ITask.getTaskDayOfWeek(day);

            if (val == 0) {
                val = 1;
            } else {
                val = 0;
            }

            setBkgColor(v, val);

            ITask.setTaskDayOfWeek(day, val);
            ITask.saveTask();
        }
    }

    public class MonthsListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int day = 0;

            switch (v.getId()) {
                case R.id.btn_jan:
                    day = 1;
                    break;
                case R.id.btn_fab:
                    day = 2;
                    break;
                case R.id.btn_mch:
                    day = 3;
                    break;
                case R.id.btn_apr:
                    day = 4;
                    break;
                case R.id.btn_may:
                    day = 5;
                    break;
                case R.id.btn_jun:
                    day = 6;
                    break;
                case R.id.btn_jul:
                    day = 7;
                    break;
                case R.id.btn_aug:
                    day = 8;
                    break;
                case R.id.btn_sep:
                    day = 9;
                    break;
                case R.id.btn_oct:
                    day = 10;
                    break;
                case R.id.btn_nov:
                    day = 11;
                    break;
                case R.id.btn_dec:
                    day = 12;
                    break;
            }

            int val = ITask.getTaskMonth(day);

            if (val == 0) {
                val = 1;
            } else {
                val = 0;
            }

            setBkgColor(v, val);

            ITask.setTaskMonth(day, val);
            ITask.saveTask();
        }
    }

    public void setBkgColor(View v, int val) {

        if (val == 0) {
            v.setBackgroundColor(getResources().getColor(R.color.btn_bkg));
        } else {
            v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
    }
}
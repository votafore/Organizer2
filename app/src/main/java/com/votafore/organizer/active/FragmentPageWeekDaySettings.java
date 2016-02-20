package com.votafore.organizer.active;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.votafore.organizer.R;
import com.votafore.organizer.model.ITaskData;

import java.util.ArrayList;

public class FragmentPageWeekDaySettings extends Fragment {

    ITaskData ITask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ITask = (ITaskData)getActivity();

        View v = inflater.inflate(R.layout.fragment_week_settings, container, false);

        ArrayList<CheckBox> mList = new ArrayList<>();

        mList.add((CheckBox)v.findViewById(R.id.ch_mnd));
        mList.add((CheckBox)v.findViewById(R.id.ch_tud));
        mList.add((CheckBox)v.findViewById(R.id.ch_wnd));
        mList.add((CheckBox)v.findViewById(R.id.ch_thd));
        mList.add((CheckBox)v.findViewById(R.id.ch_frd));
        mList.add((CheckBox)v.findViewById(R.id.ch_std));
        mList.add((CheckBox)v.findViewById(R.id.ch_snd));

        for(int i = 0; i < mList.size(); i++){

            final int index = i;

            CheckBox ch_box = mList.get(i);

            ch_box.setChecked(ITask.getTaskDayOfWeek(i+1) == 1);

            ch_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ITask.setTaskDayOfWeek(index+1, isChecked ? 1 : 0);
                    ITask.saveTask();
                }
            });
        }

        return v;
    }
}

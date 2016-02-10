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
import com.votafore.organizer.active.ITaskData;

import java.util.ArrayList;

public class FragmentPageMonthSettings extends Fragment {

    ITaskData ITask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ITask = (ITaskData)getActivity();

        View v = inflater.inflate(R.layout.fragment_month_settings, container, false);

        ArrayList<CheckBox> mList = new ArrayList<>();

        mList.add((CheckBox)v.findViewById(R.id.jan));
        mList.add((CheckBox)v.findViewById(R.id.fb));
        mList.add((CheckBox)v.findViewById(R.id.mch));
        mList.add((CheckBox)v.findViewById(R.id.apr));
        mList.add((CheckBox)v.findViewById(R.id.may));
        mList.add((CheckBox)v.findViewById(R.id.jn));
        mList.add((CheckBox)v.findViewById(R.id.jl));
        mList.add((CheckBox)v.findViewById(R.id.ag));
        mList.add((CheckBox)v.findViewById(R.id.sp));
        mList.add((CheckBox)v.findViewById(R.id.oct));
        mList.add((CheckBox)v.findViewById(R.id.nov));
        mList.add((CheckBox)v.findViewById(R.id.dec));

        for(int i = 0; i < mList.size(); i++){

            final int index = i;

            CheckBox ch_box = mList.get(i);

            ch_box.setChecked(ITask.getTaskMonth(i+1) == 1);

            ch_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ITask.setTaskMonth(index+1, isChecked ? 1 : 0);
                    ITask.saveTask();
                }
            });
        }

        return v;
    }
}

package com.votafore.organizer.active.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.votafore.organizer.R;
import com.votafore.organizer.model.ITaskData;

public class PageSettingsNotify extends Fragment {

    ITaskData ITask;

    public static PageSettingsNotify getInstance(ITaskData i_data){

        Bundle args = new Bundle();
        PageSettingsNotify f = new PageSettingsNotify();

        f.setArguments(args);
        f.setInterface(i_data);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_page_general, null);

        EditText mTitle = (EditText)v.findViewById(R.id.task_title);
        EditText mDescr = (EditText)v.findViewById(R.id.task_description);

        mTitle.setText(ITask.getTaskTitle());
        mDescr.setText(ITask.getTaskDescription());

        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ITask.setTaskTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                ITask.saveTask();
            }
        });
        mDescr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ITask.setTaskDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                ITask.saveTask();
            }
        });

        return v;
    }

    public void setInterface(ITaskData i_data){
        ITask = i_data;
    }
}

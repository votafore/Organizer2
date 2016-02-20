package com.votafore.organizer.active;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.votafore.organizer.R;
import com.votafore.organizer.model.ITaskData;

public class FragmentPageDaySettings extends Fragment {

    ITaskData ITask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ITask = (ITaskData)getActivity();

        return inflater.inflate(R.layout.fragment_day_settings, container, false);
    }
}

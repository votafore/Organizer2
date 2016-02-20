package com.votafore.organizer.active;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.votafore.organizer.R;
import com.votafore.organizer.model.ITaskData;

import java.util.ArrayList;
import java.util.List;


public class FragmentTask extends Fragment{

    // ?
    ViewPager mViewPager;
    ITaskData ITask;



    // ------------
    RecyclerView mSettingPane;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ITask = (ITaskData)getActivity();

        View v = inflater.inflate(R.layout.fragment_task, null);

        List<SettingItem> mList = new ArrayList<>();

        mList.add(new SettingItem("Custom"));
        mList.add(new SettingItem("Time"));

        mSettingPane = (RecyclerView)v.findViewById(R.id.settings_panel);

        SettingsPaneAdapter adapter = new SettingsPaneAdapter(mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        mSettingPane.setAdapter(adapter);
        mSettingPane.setLayoutManager(layoutManager);
        mSettingPane.setItemAnimator(itemAnimator);

        return v;
    }




    private class SettingsPaneAdapter extends RecyclerView.Adapter<SettingsPaneAdapter.ViewHolder> {
        List<SettingItem> mMenuItems;

        public SettingsPaneAdapter(List<SettingItem> mItems)
        {
            mMenuItems = mItems;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_settings, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            SettingItem item = mMenuItems.get(position);

            holder.title.setText(item.getTitle());
        }

        @Override
        public int getItemCount() {
            return mMenuItems.size();
        }





        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.setting_title);
            }
        }

    }

    private class SettingItem {

        private String title;

        public SettingItem(String lItem){
            title = lItem;
        }

        public String getTitle(){
            return title;
        }
    }
}

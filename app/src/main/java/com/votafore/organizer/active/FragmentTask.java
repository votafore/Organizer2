package com.votafore.organizer.active;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.votafore.organizer.R;
import com.votafore.organizer.active.page.PageSettingsNotify;
import com.votafore.organizer.active.page.PageSettingsDays;
import com.votafore.organizer.model.ITaskData;

import java.util.ArrayList;
import java.util.List;


public class FragmentTask extends Fragment{

    // ?
    ITaskData ITask;



    // ------------
    RecyclerView mSettingPane;

    public interface OnItemClickListener {
        void onItemClick(SettingItem item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ITask = (ITaskData)getActivity();

        View v = inflater.inflate(R.layout.fragment_task, null);

        List<SettingItem> mList = new ArrayList<>();

        PageSettingsNotify pGeneral = PageSettingsNotify.getInstance(ITask);

        mList.add(new SettingItem("Настройка уведомления", PageSettingsNotify.getInstance(ITask)));
//        mList.add(new SettingItem("Вариант расписания"));
        mList.add(new SettingItem("Настройка дней", PageSettingsDays.getInstance(ITask)));
//        mList.add(new SettingItem("Месяцы", FragmentPageMonthSettings.getInstance(ITask)));
//        mList.add(new SettingItem("Настройка повтора"));

        mSettingPane = (RecyclerView)v.findViewById(R.id.settings_panel);

        LinearLayoutManager         layoutManager   = new LinearLayoutManager(getActivity());
        RecyclerView.ItemAnimator   itemAnimator    = new DefaultItemAnimator();
        SettingsPaneAdapter         adapter         = new SettingsPaneAdapter(mList, new OnItemClickListener() {
            @Override
            public void onItemClick(SettingItem item) {

                FragmentManager fManager = getChildFragmentManager();

                FragmentTransaction transaction = fManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                transaction.replace(R.id.container, item.getPage());
                transaction.commit();
            }
        });

        mSettingPane.setAdapter(adapter);
        mSettingPane.setLayoutManager(layoutManager);
        mSettingPane.setItemAnimator(itemAnimator);

        FragmentManager fManager = getChildFragmentManager();
        fManager.beginTransaction().add(R.id.container, pGeneral).commit();

        return v;
    }


    private class SettingsPaneAdapter extends RecyclerView.Adapter<SettingsPaneAdapter.ViewHolder> {
        private List<SettingItem> mMenuItems;
        private final OnItemClickListener listener;

        public SettingsPaneAdapter(List<SettingItem> mItems, OnItemClickListener l_listener) {
            mMenuItems = mItems;
            listener = l_listener;
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

            holder.bind(mMenuItems.get(position), listener);
        }

        @Override
        public int getItemCount() {
            return mMenuItems.size();
        }





        class ViewHolder extends RecyclerView.ViewHolder{

            public TextView title;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.setting_title);
            }

            public void bind(final SettingItem item, final OnItemClickListener l_listener){

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        l_listener.onItemClick(item);
                    }
                });
            }
        }

    }

    private class SettingItem {

        private String title;
        private Fragment mPage;

        public SettingItem(String lItem, Fragment l_page){
            title = lItem;
            mPage = l_page;
        }

        public String getTitle(){
            return title;
        }

        public Fragment getPage(){
            return mPage;
        }
    }
}

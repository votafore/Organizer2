package com.votafore.organizer.active;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.votafore.organizer.R;
import com.votafore.organizer.model.TaskManager;

import java.util.List;

public class ActivityDialogCurTaskList extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_task_list);

        ListView curTaskList = (ListView)findViewById(R.id.cur_task_list);

        List<TaskManager.Task> mList = TaskManager.getInstance(getApplicationContext()).getTodayTask();

        ListAdapter mAdapter = new ListAdapter(getApplicationContext(), R.layout.item_cur_task, mList);
        curTaskList.setAdapter(mAdapter);

        Button closeBtn = (Button)findViewById(R.id.close_dialog);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class ListAdapter extends ArrayAdapter<TaskManager.Task> {

        private Context mContext;
        private int mResLayout;
        private List<TaskManager.Task> mTaskList;

        public ListAdapter(Context context, int resLayout, List<TaskManager.Task> list) {
            super(context, resLayout, list);

            mContext = context;
            mResLayout = resLayout;
            mTaskList = list;
        }

        @Override
        public int getCount() {
            return mTaskList.size();
        }

        @Override
        public TaskManager.Task getItem(int position) {
            return mTaskList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View listItem = View.inflate(mContext, mResLayout, null);


            TextView title = (TextView)listItem.findViewById(R.id.cur_task_title);
            TextView descr = (TextView)listItem.findViewById(R.id.cur_task_descr);
            TextView time = (TextView)listItem.findViewById(R.id.cur_task_time);

            if(getItem(position).getTitle() != null) {

                TaskManager.Task lTask = getItem(position);
                title.setText(lTask.getTitle());
                descr.setText(lTask.getDescription());
                time.setText(lTask.getHour() + ":" + lTask.getMinute());
            }

            return listItem;
        }
    }
}

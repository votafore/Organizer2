package com.votafore.organizer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.votafore.organizer.active.ActivityTask;
import com.votafore.organizer.model.TaskManager;

import java.util.List;


public class ActivityMain extends AppCompatActivity {

    ListAdapter lAdapter;
    TaskManager tManager;

    public static int FIRST_SERVICE_ALARM_ID = 10000;

    //public static String START_SHEDULE = "com.votafore.organizer.START_SHEDULE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView lTaskList = (GridView)findViewById(R.id.task_list);

        tManager = TaskManager.getInstance(getApplicationContext());
        lAdapter = new ListAdapter(getApplicationContext(), R.layout.item_task, tManager.getTaskList());

        lTaskList.setAdapter(lAdapter);

        lTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), ActivityTask.class);
                i.putExtra("ID", ((TaskManager.Task) parent.getAdapter().getItem(position)).getId());
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        lAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.add:
                // если происходит попытка создать новую задачу, то создаем ее в базе данных
                // и передаем ее ID для открытия формы редактирования
                tManager.createNewTask();

                List<TaskManager.Task> list = tManager.getTaskList();

                lAdapter.setTaskList(list);
                //lAdapter.notifyDataSetChanged();

                Intent i = new Intent(getApplicationContext(), ActivityTask.class);
                i.putExtra("ID", list.get(list.size()-1).getId());
                startActivity(i);
                break;
        }

        return true;
    }



    public class ListAdapter extends BaseAdapter {

        private Context mContext;
        private int mResLayout;
        private List<TaskManager.Task> mTaskList;

        public ListAdapter(Context context, int resLayout, List<TaskManager.Task> list) {

            mContext = context;
            mResLayout = resLayout;
            mTaskList = list;
        }

        public void setTaskList(List<TaskManager.Task> list){
            mTaskList = list;
        }

        @Override
        public long getItemId(int position) {
            return 0;
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

            listItem.setLayoutParams(new GridView.LayoutParams(250, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView title = (TextView)listItem.findViewById(R.id.template_task_list_item_title);
            TextView time = (TextView)listItem.findViewById(R.id.template_task_list_item_time);

            TaskManager.Task lTask = getItem(position);

            title.setText(lTask.getTitle());
            time.setText(DateFormat.format("HH:mm", lTask.getTime()));

            return listItem;
        }
    }
}

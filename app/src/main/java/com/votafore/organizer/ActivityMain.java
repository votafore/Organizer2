package com.votafore.organizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.votafore.organizer.active.ActivityTask;
import com.votafore.organizer.model.TaskManager;


public class ActivityMain extends AppCompatActivity {

    public static int FIRST_SERVICE_ALARM_ID = 10000;

    public static final String TASK_ID = "TASK_ID";

    TaskManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager = TaskManager.getInstance(getApplicationContext());

        TaskListAdapter mAdapter = new TaskListAdapter(mManager);

        RecyclerView mTaskList = (RecyclerView)findViewById(R.id.task_list);
        mTaskList.setAdapter(mAdapter);

        mTaskList.setHasFixedSize(true);
        mTaskList.setItemAnimator(new DefaultItemAnimator());
        mTaskList.setLayoutManager(new GridLayoutManager(this,3));
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                //TODO: вставить код по созданию задачи
                break;
        }

        return true;
    }

    public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ItemHolder>{

        private TaskManager mManager;

        public TaskListAdapter(TaskManager manager){
            this.mManager = manager;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
            return new ItemHolder(v);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {

            TaskManager.Task curTask = mManager.getTaskList().get(position);

            holder.mTitleView.setText(curTask.getTitle());
            holder.mTimeView.setText(DateFormat.format("HH:mm", curTask.getTime()));
        }

        @Override
        public int getItemCount() {
            return mManager.getTaskList().size();
        }

        public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public TextView mTitleView;
            public TextView mTimeView;

            public ItemHolder(View itemView) {
                super(itemView);

                itemView.setOnClickListener(this);

                mTitleView = (TextView)itemView.findViewById(R.id.template_task_list_item_title);
                mTimeView = (TextView)itemView.findViewById(R.id.template_task_list_item_time);
            }

            @Override
            public void onClick(View v) {

                // при клике на задаче запускаем Activity с задачей
                Intent i = new Intent(ActivityMain.this, ActivityTask.class);
                i.putExtra(TASK_ID, mManager.getTaskList().get(getAdapterPosition()).getId());

                startActivity(i);
            }
        }
    }
}

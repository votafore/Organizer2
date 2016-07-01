package com.votafore.organizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.votafore.organizer.model.TaskManager;
import com.votafore.organizer.support.AdapterTaskList;


public class ActivityMain extends AppCompatActivity {

    public static int FIRST_SERVICE_ALARM_ID = 10000;

    public static final String TASK_ID = "TASK_ID";

    TaskManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager = TaskManager.getInstance(getApplicationContext());
        mManager.createAdapter();

        RecyclerView mTaskList = (RecyclerView)findViewById(R.id.task_list);
        mTaskList.setAdapter(mManager.getListAdapter());

        mTaskList.setHasFixedSize(true);
        mTaskList.setItemAnimator(new DefaultItemAnimator());
        mTaskList.setLayoutManager(new GridLayoutManager(this,3));

        mManager.setListListener(new AdapterTaskList.IListener() {
            @Override
            public void onClick(int position, int id) {

                Intent i = new Intent(ActivityMain.this, ActivityTask.class);
                i.putExtra(TASK_ID, id);

                startActivity(i);
            }
        });

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
                Intent i = new Intent(this, ActivityTask.class);
                i.putExtra(TASK_ID, 0);

                startActivity(i);
                break;
        }

        return true;
    }
}

package com.votafore.organizer.active;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.votafore.organizer.ActivityMain;
import com.votafore.organizer.R;
import com.votafore.organizer.model.ITaskData;
import com.votafore.organizer.model.TaskManager;


public class ActivityTask extends AppCompatActivity{

    TaskManager.Task mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent i = getIntent();

        mTask = TaskManager.getInstance(this).getTask(i.getIntExtra(ActivityMain.TASK_ID, 0));


        TextInputLayout     til_title        = (TextInputLayout)findViewById(R.id.textinputlayout_title);
        EditText            et_title         = (EditText)findViewById(R.id.task_title);
        EditText            et_description   = (EditText)findViewById(R.id.task_description);
        TimePicker          timePicker       = (TimePicker)findViewById(R.id.timePicker);
        RecyclerView        rv_extraSettings = (RecyclerView)findViewById(R.id.extra_settings);


        et_title.setText(mTask.getTitle());
        et_description.setText(mTask.getDescription());

        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(mTask.getHour());
        timePicker.setCurrentMinute(mTask.getMinute());

        rv_extraSettings.setHasFixedSize(true);
        rv_extraSettings.setItemAnimator(new DefaultItemAnimator());
        rv_extraSettings.setLayoutManager(new LinearLayoutManager(this));
    }







    public class ExtraSettingsItem extends RecyclerView.Adapter<ExtraSettingsItem.ViewHolder>{


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView vh_title;
            TextView vh_description;


            public ViewHolder(View itemView) {
                super(itemView);

                vh_title        = (TextView)itemView.findViewById(R.id.extraset_title);
                vh_description  = (TextView)itemView.findViewById(R.id.extraset_description);
            }
        }
    }
}
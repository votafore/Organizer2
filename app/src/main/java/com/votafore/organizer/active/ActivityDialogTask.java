package com.votafore.organizer.active;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.votafore.organizer.R;
import com.votafore.organizer.TaskWidget;
import com.votafore.organizer.model.TaskManager;
import com.votafore.organizer.support.NotifyService;

public class ActivityDialogTask extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_task);

        Intent i = getIntent();

        TaskManager.Task mTask = TaskManager.getInstance(getApplicationContext()).getTask(i.getIntExtra("ID", 0));

        if(mTask.getId() == 0)
            return;

        setTitle(mTask.getTitle());

        TextView lDescr = (TextView)findViewById(R.id.dialog_description);
        Button btn_ok = (Button)findViewById(R.id.btn_ok);

        lDescr.setText(mTask.getDescription());

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent notifService = new Intent(getApplicationContext(), NotifyService.class);
                stopService(notifService);

                // обновляем виджет
                Intent widgetIntent = new Intent();
                widgetIntent.setAction(TaskWidget.UPDATE_WIDGETS);
                sendBroadcast(widgetIntent);
                
                finish();
            }
        });
    }

}

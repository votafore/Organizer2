package com.votafore.organizer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.votafore.organizer.model.Task;
import com.votafore.organizer.model.TaskManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ActivityAlarm extends AppCompatActivity {

    TaskManager mManager;

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mManager = TaskManager.getInstance(this);

        Intent i = getIntent();

        Task mTask = mManager.getTask(i.getIntExtra(ActivityMain.TASK_ID, 0));

        // мало ли... задачу удалили, а уведомление висит
        if (mTask.getId() == 0)
            return;

        setTitle(mTask.getTitle());

        TextView tv_description = (TextView) findViewById(R.id.notify_text);
        tv_description.setText(mTask.getDescription());

        Button btn_done = (Button)findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityAlarm.this.finish();
            }
        });

        Uri mSignal = mTask.getSignalRef();

        // проверка существует ли звуковой файл уведомления
        if (mSignal != null) {

            ParcelFileDescriptor pfd = null;

            try {
                pfd = getContentResolver().openFileDescriptor(mSignal, "r");
            } catch (FileNotFoundException e) {
                mTask.setSignalRef(null);
            }

            try {
                if (pfd != null)
                    pfd.close();
            } catch (Exception e) {

            }
        }


        // если файл существует (его ссылка останется в задаче),
        // то установим ее (ссылку) в плеере
        mSignal = mTask.getSignalRef();

        mp = new MediaPlayer();

        if (mSignal != null) {

            try {
                mp.setDataSource(this, mSignal);
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer l_mp) {
                        l_mp.start();
                    }
                });

                mp.prepareAsync();

            } catch (IOException e){
                mp.release();
                mp = MediaPlayer.create(this, R.raw.alarm);
                mp.start();
            }
        } else {
            mp.release();
            mp = MediaPlayer.create(this, R.raw.alarm);
            mp.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mp != null)
            mp.release();
    }
}

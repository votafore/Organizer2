package com.votafore.organizer.support;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.*;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;

import com.votafore.organizer.R;
import com.votafore.organizer.active.ActivityDialogTask;

public class NotifyService extends Service {

    Handler mServiceHandler;
    Looper mServiceLooper;

    HandlerThread thread;

    MediaPlayer mp;

    private class MyServiceHandler extends Handler{

        public MyServiceHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {

            if(msg.arg2 == 0){
                stopSelf(msg.arg1);
                return;
            }

            Log.v("MyTest", "обрабокта сообщения");

            Intent taskIntent = new Intent(getApplicationContext(), ActivityDialogTask.class);
            taskIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            taskIntent.putExtra("ID", msg.arg2);

            if(mp != null){
                try{
                    mp.release();
                    mp = null;
                }catch (Exception e){

                }
            }

            mp = MediaPlayer.create(getApplicationContext(), R.raw.alarm);

            startActivity(taskIntent);

            mp.start();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.v("MyTest", "создаем сообщение и посылаем на обработку");
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.arg2 = intent.getIntExtra("ID", 0);

        mServiceHandler.sendMessage(msg);

        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {

        thread = new HandlerThread("params", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new MyServiceHandler(mServiceLooper);

        Log.v("MyTest", "сервис уведомления успешно запущен");
    }

    @Override
    public void onDestroy() {
        Log.v("MyTest", "сервис уведомления успешно остановлен");

        if(mp == null)
            return;

        mp.stop();
        mp.release();
        mp = null;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

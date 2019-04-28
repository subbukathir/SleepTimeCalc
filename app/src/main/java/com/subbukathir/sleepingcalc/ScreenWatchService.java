package com.subbukathir.sleepingcalc;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.subbukathir.sleepingcalc.localstorage.database.AppDatabase;
import com.subbukathir.sleepingcalc.localstorage.dbhelper.DbInitializer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ScreenWatchService extends Service {
    private static final String TAG = ScreenWatchService.class.getSimpleName();
    Handler mHandler;
    BroadcastReceiver mReceiver;
    boolean screenStatus;


    public ScreenWatchService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
        mHandler = new Handler();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_ANSWER);
        mReceiver = new ScreenWatchReceiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
//        mHandler.postDelayed(timerTask,1000);
        if(intent!=null) {
            screenStatus = intent.getBooleanExtra("screen_off", false);
            Log.d(TAG, "status of screen : " + screenStatus);
        }
        Date date = Calendar.getInstance().getTime();
        if(screenStatus){
            Log.d(TAG,"start command screen turned off : " + date);

        }else {
            Log.d(TAG,"start command screen turned on : "+ date);
        }
        Timer timer = new Timer();
        //timer.scheduleAtFixedRate(timerTask,new Date(), 1000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Log.d(TAG,"timer task working");
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

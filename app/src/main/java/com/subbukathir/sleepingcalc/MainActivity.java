package com.subbukathir.sleepingcalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.subbukathir.sleepingcalc.localstorage.database.AppDatabase;
import com.subbukathir.sleepingcalc.localstorage.dbhelper.DbInitializer;
import com.subbukathir.sleepingcalc.localstorage.entity.ScreenOnOffEntity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DbInitializer.DataReceiveInterface {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(MainActivity.this, ScreenWatchService.class));
//        stopService(new Intent(MainActivity.this, ScreenWatchService.class));

      new DbInitializer().setListener(this);

      DbInitializer.getAllData(AppDatabase.getAppDatabase(this), DbInitializer.MODE_GETALL);
    }

    @Override
    public void onListReceived(List<ScreenOnOffEntity> screenOnOffList, int mode) {
        Log.d(TAG,"onListReceived : " + mode);
        for (ScreenOnOffEntity item : screenOnOffList){
            Log.d(TAG,"item : " + item.getDateTime() + " status : " + item.isTurnOnOff());
        }
    }

    @Override
    public void onDataReceiveError(String msg) {
        Log.d(TAG,"onDataReceiveError " + msg);
    }
}

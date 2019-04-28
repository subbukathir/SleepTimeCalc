package com.subbukathir.sleepingcalc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.subbukathir.sleepingcalc.localstorage.database.AppDatabase;
import com.subbukathir.sleepingcalc.localstorage.dbhelper.DbInitializer;
import com.subbukathir.sleepingcalc.localstorage.entity.ScreenOnOffEntity;

import java.util.Calendar;
import java.util.Date;

public class ScreenWatchReceiver extends BroadcastReceiver {
    private boolean screenOff;

    @Override
    public void onReceive(Context context, Intent intent) {
        Date currentDate = Calendar.getInstance().getTime();

        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            screenOff = true;
            DbInitializer.insertSingleData(AppDatabase.getAppDatabase(context),new ScreenOnOffEntity(screenOff, currentDate), DbInitializer.MODE_INSERT);
        }else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            screenOff = false;
            DbInitializer.insertSingleData(AppDatabase.getAppDatabase(context),new ScreenOnOffEntity(screenOff, currentDate), DbInitializer.MODE_INSERT);
        }else {
            Log.d("ScreenWatchReceiver","else");
        }

        Intent service = new Intent(context, ScreenWatchService.class);
        service.putExtra("screen_off", screenOff);
        context.startService(service);
    }
}

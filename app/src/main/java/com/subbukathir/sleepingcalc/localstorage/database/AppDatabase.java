package com.subbukathir.sleepingcalc.localstorage.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.subbukathir.sleepingcalc.localstorage.dao.ScreenOnOffDAO;
import com.subbukathir.sleepingcalc.localstorage.entity.ScreenOnOffEntity;

@Database(entities = {ScreenOnOffEntity.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE = "sleep_time_calc";
    private static AppDatabase INSTANCE;

    public abstract ScreenOnOffDAO screenOnOffDAO();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}

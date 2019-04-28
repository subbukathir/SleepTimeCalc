package com.subbukathir.sleepingcalc.localstorage.dbhelper;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.subbukathir.sleepingcalc.localstorage.database.AppDatabase;
import com.subbukathir.sleepingcalc.localstorage.entity.ScreenOnOffEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DbInitializer {
    public static final int MODE_INSERT = 10;
    public static final int MODE_GETALL = 11;
    public static final int MODE_GET_USING_DATE = 12;
    public static final int MODE_DELETE_ALL = 13;

    private static final String TAG = DbInitializer.class.getName();
    private static PopulateDbAsync mTask;
    private static int mMode;
    private static int mCount=0;
    private static ScreenOnOffEntity entityData;
    private static Date mDate;
    private static List<ScreenOnOffEntity> trackScreenList = new ArrayList<>();

    public static void insertSingleData(@NonNull final AppDatabase db, ScreenOnOffEntity entity, int mode) {
        mTask = new PopulateDbAsync(db);
        mMode = mode;
        entityData = entity;
        mTask.execute();
    }

    public static void getAllData(@NonNull final AppDatabase db, int mode) {
        mTask = new PopulateDbAsync(db);
        mMode = mode;
        mTask.execute();
    }

    public static void getAllDataByDate(@NonNull final AppDatabase db, Date date, int mode) {
        mTask = new PopulateDbAsync(db);
        mMode = mode;
        mDate = date;
        mTask.execute();
    }

    private static void insertSingle(final AppDatabase db, ScreenOnOffEntity entity) {
        Log.e(TAG, "insertSingle");
        db.screenOnOffDAO().insert(entity);
    }
    private static void getAllList(final AppDatabase db) {
        Log.e(TAG, "getAllList");
        try {
            mCount = db.screenOnOffDAO().count();
            if(mCount>0) trackScreenList = db.screenOnOffDAO().getAll();
            else Log.e(TAG,"No data found");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private static void getAllDataByDate(final AppDatabase db, Date date) {
        Log.e(TAG, "getAllList");
        try {
            trackScreenList = db.screenOnOffDAO().getAllByDate(date);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private static void deleteAll(final AppDatabase db){
        Log.e(TAG,"deleteAll");
        try {
            mCount= db.screenOnOffDAO().count();
            if(mCount>0) db.screenOnOffDAO().deleteAll();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //populateWithTestData(mDb);
            switch (mMode) {
                case MODE_INSERT:
                    insertSingle(mDb, entityData);
                    break;
                case MODE_DELETE_ALL:
                    deleteAll(mDb);
                    break;
                case MODE_GET_USING_DATE:
                    getAllDataByDate(mDb,mDate);
                    break;
                case MODE_GETALL:
                    getAllList(mDb);
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            switch (mMode) {
                case MODE_INSERT:
                    break;
                case MODE_GET_USING_DATE:
                    if(trackScreenList.size()>0) mCallback.onListReceived(trackScreenList,MODE_GET_USING_DATE);
                    else mCallback.onDataReceiveError("No data found");
                    break;
                case MODE_GETALL:
                    if(trackScreenList.size()>0) mCallback.onListReceived(trackScreenList,MODE_GETALL);
                    else mCallback.onDataReceiveError("No data found");
                    break;
            }
        }
    }
    private static DataReceiveInterface mCallback;
    public void setListener(DataReceiveInterface listener){
        mCallback = listener;
    }
    public interface DataReceiveInterface{
        void onListReceived(List<ScreenOnOffEntity> screenOnOffList, int mode);
        void onDataReceiveError(String msg);
    }
}

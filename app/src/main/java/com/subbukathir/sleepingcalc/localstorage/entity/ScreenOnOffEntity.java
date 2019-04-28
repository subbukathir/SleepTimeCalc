package com.subbukathir.sleepingcalc.localstorage.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "ScreenOnOffEntity")
public class ScreenOnOffEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="uniqueKey")
    private int uniqueKey;

    @ColumnInfo(name = "turnOnOff")
    private boolean turnOnOff;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "dateTime")
    private Date dateTime;

    public ScreenOnOffEntity( boolean turnOnOff, Date dateTime) {
        this.turnOnOff = turnOnOff;
        this.dateTime = dateTime;
    }

    public int getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(int uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public boolean isTurnOnOff() {
        return turnOnOff;
    }

    public void setTurnOnOff(boolean turnOnOff) {
        this.turnOnOff = turnOnOff;
    }

    public Date getDateTime() {
        return dateTime;
    }
}

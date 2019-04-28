package com.subbukathir.sleepingcalc.localstorage.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.subbukathir.sleepingcalc.localstorage.entity.DateConverter;
import com.subbukathir.sleepingcalc.localstorage.entity.ScreenOnOffEntity;

import java.util.Date;
import java.util.List;

@Dao
public interface ScreenOnOffDAO {

    @Query("SELECT * FROM ScreenOnOffEntity")
    List<ScreenOnOffEntity> getAll();

    @Query("SELECT COUNT(*) from ScreenOnOffEntity")
    int count();

    @TypeConverters(DateConverter.class)
    @Query("SELECT * from ScreenOnOffEntity where dateTime <= :date")
    List<ScreenOnOffEntity> getAllByDate(Date date);

 /*   @Query("SELECT COUNT(*) from ScreenOnOffEntity where modelName LIKE  :modelName")
    int countByModelName(String modelName);

    @Query("SELECT COUNT(*) from ScreenOnOffEntity where vehicleId LIKE  :vehicleId")
    int countByVehicleId(String vehicleId);

    @Query("SELECT * FROM ScreenOnOffEntity where modelName LIKE  :modelName")
    List<InspectionEntity> getAllByModelName(String modelName);

    @Query("SELECT * FROM ScreenOnOffEntity where modelName LIKE :modelName AND vehicleId LIKE  :vehicleId")
    List<InspectionEntity> getAllByVehicleId(String modelName, String vehicleId);

    @Query("SELECT DISTINCT vehicleId FROM ScreenOnOffEntity WHERE modelName LIKE  :modelName")
    List<String> getDistictVehicles(String modelName);

    @Query("SELECT DISTINCT modelName FROM ScreenOnOffEntity")
    List<String> getDistictModelName();*/

    @Insert
    void insert(ScreenOnOffEntity entity);

    @Delete
    void delete(ScreenOnOffEntity entity);

    @Query("DELETE FROM ScreenOnOffEntity")
    void deleteAll();
}

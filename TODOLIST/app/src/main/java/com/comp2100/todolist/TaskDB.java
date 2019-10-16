package com.comp2100.todolist;

import android.location.Location;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(tableName = "todo_db")
public class TaskDB {
    @PrimaryKey
    private Date createDate;

    @ColumnInfo(name = "title")
    private String Title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "isNotify")
    private boolean isNotify;

    @ColumnInfo(name = "notifyTime")
    private  String notifyTime;

    @ColumnInfo
    private String StreetName;

    @ColumnInfo(name = "notifyDate")
    private String notifyDate;

    @ColumnInfo
    private double Latitude;

    @ColumnInfo
    private double Longitude;

    @ColumnInfo(name = "isdone")
    private boolean isdone;

    @ColumnInfo
    private int minute;
    @ColumnInfo
    private int hour;
    @ColumnInfo
    private int day;
    @ColumnInfo
    private int month;
    @ColumnInfo
    private int year;


}

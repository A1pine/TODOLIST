package com.comp2100.todolist;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TaskDB.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class DatabaseHelper extends RoomDatabase {
     public abstract taskDao taskDao();
}

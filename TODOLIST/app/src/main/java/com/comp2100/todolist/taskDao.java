package com.comp2100.todolist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface taskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskDB task);

    @Update
    void update(TaskDB task);

    @Delete
    void delete(TaskDB task);

    @Query("select * from taskdb")
    List<TaskDB> getAll();

    @Query("select * from taskdb where catalog = :taskcatalog")
    List<TaskDB> gettask(String taskcatalog);
}

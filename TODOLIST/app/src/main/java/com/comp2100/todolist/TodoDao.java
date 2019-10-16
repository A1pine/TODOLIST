package com.comp2100.todolist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskDB task);

    @Update
    void update(TaskDB task);

    @Delete
    void delete(TaskDB task);

    @Query("select * from todo_db")
    List<TaskDB> findAll();
}

package com.comp2100.todolist;

import org.junit.Test;

import static org.junit.Assert.*;

public class AddTaskTest {
    AddTask newTask = new AddTask();
    String hour = "2";

    @Test
    public void getStreetName() {

    }

    @Test
    public void get0() {
        //Test get one more 0 function
        assertEquals("02" , newTask.get0(hour));
    }
}
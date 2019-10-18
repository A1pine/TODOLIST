package com.comp2100.todolist;

public class TaskEvent {
    TaskDB tasks;
    //Send tasks to the cardView List
    TaskEvent(TaskDB tasks){
        this.tasks = tasks;
    }
}

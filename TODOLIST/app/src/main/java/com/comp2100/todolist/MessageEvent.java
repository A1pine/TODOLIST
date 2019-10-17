package com.comp2100.todolist;

public class MessageEvent {
    TaskDB todo;
    MessageEvent(TaskDB tasks){
        todo = tasks;
    }
}

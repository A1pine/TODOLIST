package com.comp2100.todolist;

import java.util.ArrayList;
import java.util.List;

class Task {
    String date;
    String Title;
    String location;
    public List<Task> tasks;
    Task(){}
    Task(String title , String date , String location){
        this.Title = title;
        this.date = date;
        this.location = location;
    }


    // This method creates an ArrayList that has three task objects
    public void initializeData(){
        tasks = new ArrayList<>();
        tasks.add(new Task("Task1" , "02/10/2019" , "home"));
        tasks.add(new Task("Task2" , "03/10/2019" , "school"));
        tasks.add(new Task("Task3" , "03/10/2019" , "outside"));
    }
}

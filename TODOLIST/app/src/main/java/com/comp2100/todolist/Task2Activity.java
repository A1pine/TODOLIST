package com.comp2100.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Task2Activity extends AppCompatActivity {

    List<collection> collections;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_taskrecyclerview);

        collections = new ArrayList<>();
        collections.add(new collection("Personal",R.drawable.ic_person_black_24dp));
        collections.add(new collection("Work",R.drawable.ic_work_black_24dp));
        collections.add(new collection("Meeting",R.drawable.ic_event_note_black_24dp));
        collections.add(new collection("Shopping",R.drawable.ic_local_grocery_store_black_24dp));
        collections.add(new collection("Party",R.drawable.ic_local_play_black_24dp));
        collections.add(new collection("Study",R.drawable.ic_school_black_24dp));


        RecyclerView myrecycler = (RecyclerView) findViewById(R.id.taskRecyclerview);
        RecyclerViewTask mytask = new RecyclerViewTask(this,collections);
        mytask.setLayoutManger(new GridLayoutManager(this,2));

    }
}

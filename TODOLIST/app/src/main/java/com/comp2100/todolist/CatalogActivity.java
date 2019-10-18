package com.comp2100.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class CatalogActivity extends AppCompatActivity {
    RecyclerView rv;
    RVAdapter adapter = new RVAdapter();
    LinearLayoutManager llm;
    String taskcatalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String catalog = bundle.getString("CatalogActivity" , "");

        rv = findViewById(R.id.CatalogRV);

        //Get the List using database

        llm = new LinearLayoutManager(getApplicationContext());
        taskcatalog = catalog;
        getTasks();
//        getTasks("Personal");


    }
    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<TaskDB>> {

            //Link to Database and return the List;
            @Override
            protected List<TaskDB> doInBackground(Void... voids) {
                List<TaskDB> taskList = DatabaseClient
                        .getInstance(CatalogActivity.this)
                        .getAppDatabase()
                        .taskDao()
                        .gettask(taskcatalog);
                Log.e("GotListw" , taskcatalog);
                return taskList;
            }

            @Override
            protected void onPostExecute(List<TaskDB> tasks) {
                super.onPostExecute(tasks);
//                Toast toast=Toast.makeText(getContext(), "Sent!" ,  Toast.LENGTH_SHORT);
//                toast.show();

                Log.e("GotList" , tasks.toString());
                adapter = new RVAdapter(tasks);
                rv.setLayoutManager(llm);
                rv.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}

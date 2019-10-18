package com.comp2100.todolist;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TaskViewHolder>{
    static List<TaskDB> tasks;

    private Context mCtx;
    public RVAdapter(Context mCtx, List<TaskDB> taskList) {
        this.mCtx = mCtx;
        this.tasks = taskList;
    }
    RVAdapter(){

    }
    RVAdapter(List<TaskDB> tasks){
        this.tasks = tasks;
    }

    private static View DialogView;
    public class TaskViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView taskTitle;
        TextView tasklocation;
        TextView taskdate;
        RelativeLayout mycolor;

        public TaskViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.CardView);
            taskTitle = itemView.findViewById(R.id.TextTitle);
            tasklocation = itemView.findViewById(R.id.PlaceText);
            taskdate = itemView.findViewById(R.id.DueText);
            mycolor = itemView.findViewById(R.id.LeftSideColor);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskDB task = tasks.get(getAdapterPosition());
                    showDialogForView(v , task);

//                    Toast toast=Toast.makeText(v.getContext(), task.getTitle(),  Toast.LENGTH_LONG);
//                    toast.show();
                }
            });
        }
    }
    private void showDialogForView(View v, TaskDB taskDB) {
        LayoutInflater li = LayoutInflater.from(v.getContext());
        if (DialogView != null) {
            ViewGroup parent = (ViewGroup) DialogView.getParent();
            if (parent != null)
                parent.removeView(DialogView);
        }
        try {
            DialogView = li.inflate(R.layout.popupwindows , null);
            TextView textTitle = DialogView.findViewById(R.id.title);
            TextView descTitle = DialogView.findViewById(R.id.description);
            textTitle.setHint(taskDB.getTitle());
            //textTitle.setHint(taskDB.getDescription());

        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext() , R.style.Theme_MaterialComponents_Light_Dialog_Alert)
                .setTitle("Edit")
                .setIcon(R.mipmap.ic_launcher)
                .setView(DialogView);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                createNotificationChannel(v);
            }
        });
        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tasks.remove(taskDB);
                DatabaseClient
                        .getInstance(v.getContext())
                        .getAppDatabase()
                        .taskDao()
                        .delete(taskDB);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_base_swipe_list, viewGroup, false);
        TaskViewHolder Tvh = new TaskViewHolder(v);
        return Tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        TaskDB nowTask = tasks.get(i);
        taskViewHolder.taskTitle.setText(nowTask.getTitle());
        taskViewHolder.tasklocation.setText(nowTask.getStreetName());
        String time = nowTask.getDay() + "/" + nowTask.getMonth() + "/" + nowTask.getYear() + "  " + nowTask.getHour() + ":" + nowTask.getMinute();
        taskViewHolder.taskdate.setText(time);
        String catalog = nowTask.getCatalog();
//        Toast.makeText(, "Saved", Toast.LENGTH_LONG).show();
        if(catalog.equals("Personal"))
            taskViewHolder.mycolor.setBackgroundColor(0xFFEB3B);

//        taskViewHolder.taskdate.setText(tasks.get(i).getDay());
//        Toast()
    }
    //set notification channel
    private static void createNotificationChannel(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyNotification", "MyNotification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = v.getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(v.getContext(), "MyNotification")
                .setContentTitle("This is my title")
                .setSmallIcon(R.drawable.ic_one)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentText("This is my test");
        NotificationManagerCompat manager = NotificationManagerCompat.from(v.getContext());
        manager.notify(999, builder.build());

    }
    @Override
    public int getItemCount() {
        return tasks.size();
    }
    public void addRow(TaskDB data) {
        tasks.add(data);
        notifyDataSetChanged();
    }


}

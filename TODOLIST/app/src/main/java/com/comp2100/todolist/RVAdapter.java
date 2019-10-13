package com.comp2100.todolist;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.os.Build;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TaskViewHolder>{
    List<Task> tasks;

    RVAdapter(List<Task> tasks){
        this.tasks = tasks;
    }

    private static View DialogView;
    public  static class TaskViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView taskTitle;
        TextView tasklocation;
        TextView taskdate;
        public TaskViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.CardView);
            taskTitle = itemView.findViewById(R.id.TextTitle);
            tasklocation = itemView.findViewById(R.id.PlaceText);
            taskdate = itemView.findViewById(R.id.DueText);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogForView(v);
                }
            });
        }
    }
    private static void showDialogForView(View v) {
        LayoutInflater li = LayoutInflater.from(v.getContext());
        if (DialogView != null) {
            ViewGroup parent = (ViewGroup) DialogView.getParent();
            if (parent != null)
                parent.removeView(DialogView);
        }
        try {
            DialogView = li.inflate(R.layout.popupwindows , null);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                .setTitle("Edit")
                .setIcon(R.mipmap.ic_launcher)
                .setView(DialogView);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createNotificationChannel(v);
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
        taskViewHolder.taskTitle.setText(tasks.get(i).Title);
        taskViewHolder.tasklocation.setText(tasks.get(i).location);
        taskViewHolder.taskdate.setText(tasks.get(i).date);
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
}

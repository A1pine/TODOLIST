package com.comp2100.todolist;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TaskViewHolder> implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {
    static List<TaskDB> tasks;

    private Context mCtx;


    Integer SelectMonth;
    Integer SelectYear;
    Integer SelectDay;
    Integer SelectHour;
    Integer SelectMinute;
    public RVAdapter(Context mCtx, List<TaskDB> taskList) {
        this.mCtx = mCtx;
        this.tasks = taskList;
    }
    RVAdapter(){

    }
    RVAdapter(List<TaskDB> tasks){
        this.tasks = tasks;
    }




    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        SelectMonth = dayOfMonth;
        SelectYear = year;
        SelectDay = dayOfMonth;
        TextView dateText = DialogView.findViewById(R.id.dateText);
        dateText.setText(date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String pickedTime = hourOfDay + ":" + minute;
        SelectHour = hourOfDay;
        SelectMinute = minute;

        TextView timeText = DialogView.findViewById(R.id.timeText);
        timeText.setText(pickedTime);
    }
    private static View DialogView;
    public class TaskViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView taskTitle;
        TextView tasklocation;
        TextView taskdate;
        TextView LeftDays;
        TextView LeftorPast;
        CheckBox isdownBox;
        RelativeLayout mycolor;

        public TaskViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.CardView);
            taskTitle = itemView.findViewById(R.id.TextTitle);
            tasklocation = itemView.findViewById(R.id.PlaceText);
            taskdate = itemView.findViewById(R.id.DueText);
            mycolor = itemView.findViewById(R.id.LeftSideColor);
            LeftDays = itemView.findViewById(R.id.DayleftText);
            LeftorPast = itemView.findViewById(R.id.isGone);
            isdownBox = itemView.findViewById(R.id.isDownBox);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskDB task = tasks.get(getAdapterPosition());
//                    EventBus.getDefault().postSticky(new TaskEvent(task));
                    Intent intent= new Intent(v.getContext(),DialogActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putLong("EditActivity", task.getCreateDate().getTime());
//                    intent.putExtras(bundle);
                    intent.putExtra("task", task);
                    v.getContext().startActivity(intent);

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
            textTitle.setText(taskDB.getTitle());
            descTitle.setText(taskDB.getDescription());
            TextView dateText = DialogView.findViewById(R.id.dateText);
            dateText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            RVAdapter.this,
                            now.get(Calendar.YEAR), // Initial year selection
                            now.get(Calendar.MONTH), // Initial month selection
                            now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                    );
                    dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                    dpd.show(((AppCompatActivity)v.getContext()).getSupportFragmentManager(), "Datepickerdialog");
                }
            });
            TextView timeText = DialogView.findViewById(R.id.timeText);
            timeText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar now = Calendar.getInstance();
                    TimePickerDialog tpd = TimePickerDialog.newInstance(
                            RVAdapter.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            now.get(Calendar.SECOND),
                            true
                    );
                    tpd.setVersion(TimePickerDialog.Version.VERSION_2);
                    tpd.show(((AppCompatActivity)v.getContext()).getSupportFragmentManager(), "Datepickerdialog");
                }
            });


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

//                createNotificationChannel(v);
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
            taskViewHolder.mycolor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFEB3B")));
        if(catalog.equals("Meeting"))
            taskViewHolder.mycolor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E91E63")));
        if(catalog.equals("Party"))
            taskViewHolder.mycolor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0288D1")));
        if(catalog.equals("Work"))
            taskViewHolder.mycolor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        if(catalog.equals("Shopping"))
            taskViewHolder.mycolor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
        if(catalog.equals("Study"))
            taskViewHolder.mycolor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#673AB7")));
        if(nowTask.isIsdone()){
            taskViewHolder.mycolor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#616161")));
        }
        SimpleDateFormat Notifyfmt = new SimpleDateFormat("dd/MM/yyyy HH:mm" , Locale.US);
        SimpleDateFormat Leftfmt = new SimpleDateFormat("dd/MM/yyyy" , Locale.US);
        String strtime = nowTask.getDay() + "/" + nowTask.getMonth() + "/" +nowTask.getYear() + " " + nowTask.getHour() + ":" + nowTask.getMinute();
        try {
            Date Lefdate = Leftfmt.parse(strtime);
            Date now = new Date(System.currentTimeMillis());
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(now);
            cal2.setTime(Lefdate);
// Clear Hours and minutes
            cal1.set(Calendar.HOUR_OF_DAY, 0);
            cal1.set(Calendar.MINUTE, 0);
            cal1.set(Calendar.SECOND, 0);
            cal1.set(Calendar.MILLISECOND, 0);
            now = cal1.getTime();
            Log.e("LastTime", now.toString());
            Log.e("LastTime" , strtime);
            int days = (int) ((Lefdate.getTime() - now.getTime()) / (1000*3600*24));
            if(days >= 0){
                taskViewHolder.LeftDays.setText(String.valueOf(days));
            }
            else{
                days = Math.abs(days);
                taskViewHolder.LeftDays.setText(String.valueOf(days));
                taskViewHolder.LeftorPast.setText("past");
                taskViewHolder.mycolor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#616161")));
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Date Notifydate = Notifyfmt.parse(strtime);
            Long Notifytime = Notifydate.getTime();


        } catch (ParseException e) {
            e.printStackTrace();
        }
//        taskViewHolder.taskdate.setText(tasks.get(i).getDay());
//        Toast()
    }
    //set notification channel
    private static void createNotificationChannel(View v , Long time) {
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
                .setContentText("This is my test")
                .setWhen(time);
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

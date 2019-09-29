package com.comp2100.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //change star button color
        View view;
        view = this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.coloryellow);

        //set notificationbutton
        ImageButton starButton = findViewById(R.id.notificationButton);
        starButton.setOnClickListener((e)->{
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(),"Time Picker");
        });

        NotificationManagerCompat notificationManagerCompat;
        notificationManagerCompat = NotificationManagerCompat.from(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("this is notification")
                .setContentText("ok...")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("fine..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManagerCompat.notify(1,builder.build());





        //Set year and month
        TextView YearMonthText = findViewById(R.id.yearmonthText);
        YearMonthText.setText(getYearMonth());


//        int week = calendar.get(Calendar.DAY_OF_WEEK);
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE" , Locale.ENGLISH);
//        sdf
        //Set Today's top calendar
        TextView WeekTodayText = findViewById(R.id.WeekTodayText);
        TextView DateTodayText = findViewById(R.id.DateTodayText);
        DateTodayText.setText(getDate(0));
        WeekTodayText.setText(getWeek(0));

        //Set tomorrow's calendar
        TextView WeekTmrdayText = findViewById(R.id.WeekTmrText);
        TextView DateTmrText = findViewById(R.id.DateTmrText);
        DateTmrText.setText(getDate(1));
        WeekTmrdayText.setText(getWeek(1));

        //Set yesterday's calendar
        TextView WeekYesdayText = findViewById(R.id.WeekYesText);
        TextView DateYesText = findViewById(R.id.DateYesText);
        DateYesText.setText(getDate(-1));
        WeekYesdayText.setText(getWeek(-1));

        //Set day after tomorrow's calendar
        TextView WeekDaTdayText = findViewById(R.id.WeekDaTText);
        TextView DateDaTText = findViewById(R.id.DateDaTText);
        DateDaTText.setText(getDate(2));
        WeekDaTdayText.setText(getWeek(2));

        //Set day before yesterday's top calendar
        TextView WeekDbydayText = findViewById(R.id.WeekDbyText);
        TextView DateDbyText = findViewById(R.id.DateDbYText);
        DateDbyText.setText(getDate(-2));
        WeekDbydayText.setText(getWeek(-2));
    }
    protected String getDate(Integer from){
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        return Integer.toString(date + from);

    }
    protected String getWeek(Integer from){
        Calendar calendar = Calendar.getInstance();
        String week = "";
        switch ((calendar.get(Calendar.DAY_OF_WEEK) + from % 7)){
            case Calendar.MONDAY:
                week = "Mon.";
                break;
            case Calendar.TUESDAY:
                week = "Tue.";
                break;
            case Calendar.WEDNESDAY:
                week = "Wed.";
                break;
            case Calendar.THURSDAY:
                week = "Thurs.";
                break;
            case Calendar.FRIDAY:
                week = "Fri.";
                break;
            case Calendar.SATURDAY:
                week = "Sat.";
                break;
            case Calendar.SUNDAY:
                week = "Sun.";
                break;
                default:
                    break;
        }
        return week;
    }
    protected String getYearMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String month = "";

        switch (calendar.get(Calendar.MONTH)){
            case 0 :
                month = "Jan";
                break;
            case 1 :
                month = "Feb";
                break;
            case 2 :
                month = "Mar";
                break;
            case 3 :
                month = "Apr";
                break;
            case 4 :
                month = "May";
                break;
            case 5 :
                month = "June";
                break;
            case 6 :
                month = "July";
                break;
            case 7 :
                month = "Aug";
                break;
            case 8 :
                month = "Sept";
                break;
            case 9 :
                month = "Oct";
                break;
            case 10 :
                month = "Nov";
                break;
            case 11 :
                month = "Dec";
                break;
            default:
                break;
        }
        return month + ", " + year;


    }

    //change the star background color
    public void goYellow(View view){

        view.setBackgroundResource(R.color.coloryellow);
    }

    //set notification time
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        TextView textView = findViewById(R.id.timeView);
        String string = "Time is" + SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        textView.setText(string);
    }
    private void startalarm(Calendar calendar){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertReceive.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
    }
    private void cancelalarm(Calendar calendar){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceive.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.cancel(pendingIntent);
    }

}


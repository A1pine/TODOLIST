package com.comp2100.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ImageButton addButton;//Create object for AddButton



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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

        addButton=findViewById(R.id.AddButton);// jump to 'NewReminder' page
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,NewReminder.class);
                startActivity(intent1);
            }
        });











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
}

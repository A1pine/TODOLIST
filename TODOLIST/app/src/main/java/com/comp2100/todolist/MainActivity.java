package com.comp2100.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, TaskFragment.OnFragmentInteractionListener  {
    private static final String TAG="PopupWindows (Select date)";

    private BottomNavigationView bottomNavigationView;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private MenuItem menuItem;
    private TextView mDisplayDate;
    Dialog myPopupWindows;
//    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





//        mDisplayDate=(TextView) findViewById(R.id.selectDate);
//        mDisplayDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar cal=Calendar.getInstance();
//                int year=cal.get(Calendar.YEAR);
//                int month=cal.get(Calendar.MONTH);
//                int day=cal.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog dialog=new DatePickerDialog(MainActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        mDateSetListener,year,month,day);
//                dialog.getDatePicker().setBackground(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//
//            }
//        });


        //popup windows select date 2

//        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                month=month+1;
//                Log.d(TAG,"onDateSet:mm/dd/yyy"+month+"/" +day + "/" +year);
//
//                String date=month+"/"+"/"+year;
//                mDisplayDate.setText(date);
//
//
//
//
//            }
//        };

        //define pop up windows
        myPopupWindows = new Dialog(this);

        //change star button color
        View view;
        view = this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.coloryellow);



        //set notificationbutton
//        ImageButton starButton = findViewById(R.id.notificationButton);
//        starButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View e) {
//                DialogFragment timePicker = new TimePickerFragment();
//                timePicker.show(MainActivity.this.getSupportFragmentManager(), "Time Picker");
//            }
//        });

//        NotificationManagerCompat notificationManagerCompat;
//        notificationManagerCompat = NotificationManagerCompat.from(this);
 //       NotificationCompat.Builder builder = new NotificationCompat.Builder(this,App.CHANNEL_1_ID)
 //               .setSmallIcon(R.drawable.ic_notification)
 //               .setContentTitle("this is notification")
  //              .setContentText("ok...")
  //              .setStyle(new NotificationCompat.BigTextStyle().bigText("fine..."))
   //             .setPriority(NotificationCompat.PRIORITY_DEFAULT);
  //      notificationManagerCompat.notify(1,builder.build());

        // define the floating action button at the middle
        FloatingActionButton Addtask_button = findViewById(R.id.addtask);// jump to 'NewReminder' page
        //set the floating action button's action
        Addtask_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this,NewReminder.class);
                startActivity(intent1);
            }
        });
        //set the switchable view
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        viewPager = findViewById(R.id.vp);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //default: none of the button at the bottom tab bar selected;
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                //one of the item is selected
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        List<Fragment> list = new ArrayList<>();
        //add the two fragment to the bottom tab bar;
        list.add(HomeFragment.newInstance("Home"));
        list.add(TaskFragment.newInstance("Task"));
        viewPagerAdapter.setList(list);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //set the two button of the tab bar to fragments
            menuItem = item;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_task:
                    viewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    //change the star background color
    public void goYellow(View view){

        view.setBackgroundResource(R.color.coloryellow);
    }

    //set notification time
  //  @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
 //       Calendar calendar = Calendar.getInstance();
  //      calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
   //     calendar.set(Calendar.MINUTE,minute);
   //     calendar.set(Calendar.SECOND,0);
  //      TextView textView = findViewById(R.id.timeView);
  //      String string = "Time is" + SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
  //      textView.setText(string);
 //   }
 //   private void startalarm(Calendar calendar){
  //      AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
  //      Intent intent = new Intent(this,AlertReceive.class);
 //       PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
  //      alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
 //   }
 //   private void cancelalarm(Calendar calendar){
  //      AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
  //      Intent intent = new Intent(this, AlertReceive.class);
 //       PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
  //      alarmManager.cancel(pendingIntent);
  //  }

    //Popup Windows activity
    public void ShowPopup(View v) {
        Button buttonCancel;
        Button buttonsave;
//when click popup savebuton it wil have notification

        myPopupWindows.setContentView(R.layout.popupwindows);
        buttonCancel=myPopupWindows.findViewById(R.id.buttonCancel);
        buttonsave = myPopupWindows.findViewById(R.id.btnfollow);


        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotificationChannel();

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPopupWindows.dismiss();
            }
        });

        myPopupWindows.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myPopupWindows.show();
    }


//set notification channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyNotification", "MyNotification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotification")
                .setContentTitle("This is my title")
                .setSmallIcon(R.drawable.ic_one)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentText("This is my test");
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());

    }


}


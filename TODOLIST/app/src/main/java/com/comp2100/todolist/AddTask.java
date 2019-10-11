package com.comp2100.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class AddTask extends AppCompatActivity implements OnMapReadyCallback,  DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {
    String strBtnSelected = "unInit";

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        TextView dateText = findViewById(R.id.dateText);
        dateText.setText(date);

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String pickedTime = hourOfDay + ":" + minute;
        TextView timeText = findViewById(R.id.timeText);
        timeText.setText(pickedTime);
    }

//    @Override
//    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
//
//    }

    public class BtnSelected implements View.OnClickListener {
        public BtnSelected(String str) {
            bntID = str;
        }

        final public String bntID;

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            strBtnSelected = bntID;
            RadioGroup grp1 = findViewById(R.id.rgcolour);
            RadioGroup grp2 = findViewById(R.id.belowcolour);
            if (bntID.equals("1") || bntID.equals("2") || bntID.equals("3")) {
                grp2.clearCheck();
            } else if (bntID.equals("4") || bntID.equals("5")
                    || bntID.equals("6")) {
                grp1.clearCheck();
            }
        }
    }
    BtnSelected btnListener1 = new BtnSelected("1");
    BtnSelected btnListener2 = new BtnSelected("2");
    BtnSelected btnListener3 = new BtnSelected("3");
    BtnSelected btnListener4 = new BtnSelected("4");
    BtnSelected btnListener5 = new BtnSelected("5");
    BtnSelected btnListener6 = new BtnSelected("6");
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
        RadioButton PersonalBtn = findViewById(R.id.PersonalButton);
        RadioButton MeetingBtn = findViewById(R.id.MeetingButton);
        RadioButton PartyBtn = findViewById(R.id.PartyButton);
        RadioButton WorkBtn = findViewById(R.id.WorkButton);
        RadioButton ShoppingBtn = findViewById(R.id.ShoppingButton);
        RadioButton StudyBtn = findViewById(R.id.StudyButton);
        PersonalBtn.setOnClickListener(btnListener1);
        MeetingBtn.setOnClickListener(btnListener2);
        PartyBtn.setOnClickListener(btnListener3);
        WorkBtn.setOnClickListener(btnListener4);
        ShoppingBtn.setOnClickListener(btnListener5);
        StudyBtn.setOnClickListener(btnListener6);
        TextView dateText = findViewById(R.id.dateText);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddTask.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });
        TextView timeText = findViewById(R.id.timeText);
        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddTask.this,
                        now.get(Calendar.HOUR),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND),
                        false
                );
                tpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        double lat = 40.73;
        double lng = -73.99;
        LatLng appointLoc = new LatLng(lat, lng);

        // 移动地图到指定经度的位置
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(appointLoc));
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        //添加标记到指定经纬度
        googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
    }
}

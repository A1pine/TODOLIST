package com.comp2100.todolist;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import org.greenrobot.eventbus.*;
import org.joda.time.Hours;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;


import java.io.IOException;
import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;

public class AddTask extends AppCompatActivity implements OnMapReadyCallback,  DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {
    String strBtnSelected = "unInit";
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
   LatLng currtlocation;
    Integer SelectMonth;
    Integer SelectYear;
    Integer SelectDay;
    Integer SelectHour;
    Integer SelectMinute;
    String SelectTime;


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        SelectMonth = dayOfMonth;
        SelectYear = year;
        SelectDay = dayOfMonth;
        TextView dateText = findViewById(R.id.dateText);
        dateText.setText(date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String pickedTime = hourOfDay + ":" + minute;
        SelectHour = hourOfDay;
        SelectMinute = minute;

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

        //Register Subsriber
        onResume();
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain ONLY MapView SDK
        // objects or sub-Bundles.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getView().setClickable(true);
        mapFragment.getMapAsync(this);

        Button testButton = findViewById(R.id.Testbutton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTask.this, MapSelectActivity.class);
                startActivity(intent);
                Toast toast=Toast.makeText(getApplicationContext(), "Clicked" ,  Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Make the radiobuttons can only be chosen once
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
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
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
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND),
                        true
                );
                tpd.setVersion(TimePickerDialog.Version.VERSION_2);
                tpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTask.this.finish();
            }
        });
        Button SaveButton = findViewById(R.id.SaveButton);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveTask();
                RadioGroup grp1 = findViewById(R.id.rgcolour);
                RadioGroup grp2 = findViewById(R.id.belowcolour);
                RadioButton CheckedButton = findViewById(grp1.getCheckedRadioButtonId() == -1?grp2.getCheckedRadioButtonId() : grp1.getCheckedRadioButtonId());
                String catalogType = String.valueOf(CheckedButton.getText());

                Log.e("SaveButton", catalogType);
                SaveButton.setClickable(false);
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(500); // two minute interval
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
        }
    }
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //move map camera
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            List<Location> locationList = locationResult.getLocations();
//            if (locationList.size() > 0) {
//                //The last location in the list is the newest
//                Location location = locationList.get(locationList.size() - 1);
//                currtlocation = location;
//                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
//                mLastLocation = location;
//                if (mCurrLocationMarker != null) {
//                    mCurrLocationMarker.remove();
//                }
//
//                //Place current location marker
//                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(latLng);
//                markerOptions.title("Current Position");
//                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
//                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
//                mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
//                //move map camera
//                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//                getLocation(location.getLatitude() , location.getLongitude());
            }
        }
    };
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(AddTask.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                        mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
                        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
                        mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
                        mGoogleMap.getUiSettings().setScrollGesturesEnabled(false);

                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    public String getStreetName(LatLng curlocation) {
        Double lat = curlocation.latitude;
        Double lng = curlocation.longitude;
        Geocoder geocoder = new Geocoder(AddTask.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);

            Toast toast=Toast.makeText(getApplicationContext(), add ,  Toast.LENGTH_LONG);
            return add;



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return "Address is not Available";
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent msg){
        Place place = msg.place;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        mCurrLocationMarker = mGoogleMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
        currtlocation = place.getLatLng();
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 14));
//        currtlocation = place.getLatLng()\
//        onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void saveTask(){
        TextView TitleText = findViewById(R.id.title);
        TextView DespText = findViewById(R.id.description);
        RadioGroup grp1 = findViewById(R.id.rgcolour);
        RadioGroup grp2 = findViewById(R.id.belowcolour);
        RadioButton CheckedButton = findViewById(grp1.getCheckedRadioButtonId() == -1?grp2.getCheckedRadioButtonId() : grp1.getCheckedRadioButtonId());
        String StreetName = "";
        String catalogType = String.valueOf(CheckedButton.getText());
        if(currtlocation != null)
            StreetName = getStreetName(currtlocation);
        if(TitleText.getText() == null)
            Toast.makeText(AddTask.this, "Please input Title", Toast.LENGTH_LONG).show();
        else if(DespText.getText() == null)
            Toast.makeText(AddTask.this, "Please input Description", Toast.LENGTH_LONG).show();
        else if(mCurrLocationMarker == null)
            Toast.makeText(AddTask.this, "Please Select a place", Toast.LENGTH_LONG).show();
        else if (SelectHour == null)
            Toast.makeText(AddTask.this, "Please Select time", Toast.LENGTH_LONG).show();
        else if(SelectYear == null)
            Toast.makeText(AddTask.this, "Please Select a date", Toast.LENGTH_LONG).show();
        else{
            String Title  = String.valueOf(TitleText.getText());
            String  Description = String.valueOf(DespText.getText());


            String finalStreetName = StreetName;

            class SaveTask extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {

                    //creating a task
                    TaskDB newTask = new TaskDB();
                    newTask.setTitle(Title);
                    newTask.setDescription(Description);
                    newTask.setStreetName(finalStreetName);
                    newTask.setLatitude(currtlocation.latitude);
                    newTask.setLongitude(currtlocation.longitude);
                    Date date = new Date(System.currentTimeMillis());
                    newTask.setNotify(true);
                    newTask.setHour(SelectHour);
                    newTask.setMinute(SelectMinute);
                    newTask.setMonth(SelectMonth);
                    newTask.setDay(SelectDay);
                    newTask.setYear(SelectYear);
                    newTask.setCreateDate(date);
                    newTask.setIsdone(false);

                    newTask.setCatalog(catalogType);



                    //adding to database
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .taskDao()
                            .insert(newTask);

                    EventBus.getDefault().post(new TaskEvent(newTask));
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    finish();
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

                }

            }
            SaveTask st = new SaveTask();
            st.execute();
//            todoDao.insert(newTask);



            AddTask.this.finish();
        }
    }
}

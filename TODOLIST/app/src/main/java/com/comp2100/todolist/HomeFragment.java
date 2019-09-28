package com.comp2100.todolist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    protected static Fragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putString("name", name);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //Set year and month
        TextView YearMonthText = view.findViewById(R.id.yearmonthText);
        YearMonthText.setText(getYearMonth());


//        int week = calendar.get(Calendar.DAY_OF_WEEK);
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE" , Locale.ENGLISH);
//        sdf
        //Set Today's top calendar
        TextView WeekTodayText = view.findViewById(R.id.WeekTodayText);
        TextView DateTodayText = view.findViewById(R.id.DateTodayText);
        DateTodayText.setText(getDate(0));
        WeekTodayText.setText(getWeek(0));

        //Set tomorrow's calendar
        TextView WeekTmrdayText = view.findViewById(R.id.WeekTmrText);
        TextView DateTmrText = view.findViewById(R.id.DateTmrText);
        DateTmrText.setText(getDate(1));
        WeekTmrdayText.setText(getWeek(1));

        //Set yesterday's calendar
        TextView WeekYesdayText = view.findViewById(R.id.WeekYesText);
        TextView DateYesText = view.findViewById(R.id.DateYesText);
        DateYesText.setText(getDate(-1));
        WeekYesdayText.setText(getWeek(-1));

        //Set day after tomorrow's calendar
        TextView WeekDaTdayText = view.findViewById(R.id.WeekDaTText);
        TextView DateDaTText = view.findViewById(R.id.DateDaTText);
        DateDaTText.setText(getDate(2));
        WeekDaTdayText.setText(getWeek(2));

        //Set day before yesterday's top calendar
        TextView WeekDbydayText = view.findViewById(R.id.WeekDbyText);
        TextView DateDbyText = view.findViewById(R.id.DateDbYText);
        DateDbyText.setText(getDate(-2));
        WeekDbydayText.setText(getWeek(-2));
        // Inflate the layout for this fragment
        return view;
    }
    private String getDate(Integer from){
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        return Integer.toString(date + from);

    }
    private String getWeek(Integer from){
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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.comp2100.todolist;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstancesState){
        Calendar calendar = Calendar.getInstance();
        int minute = calendar.get((Calendar.MINUTE));
        int hour = calendar.get((Calendar.HOUR_OF_DAY));
        return new TimePickerDialog(getActivity(),
                (TimePickerDialog.OnTimeSetListener)getActivity(), minute,hour,
                android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

}
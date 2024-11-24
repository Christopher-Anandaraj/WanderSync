package com.example.sprintproject.view.ui.community;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/*
    This method creates a DatePicker fragment which allows the user to pick a date using
    a calendar view.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface OnDateSelectedListener {
        void onDateSelected(int year, int month, int day);
    }

    private OnDateSelectedListener listener;

    private void setListener(OnDateSelectedListener listener) {
        this.listener = listener;
    }

    //make a new instance when the data is picked
    public static DatePickerFragment newInstance(OnDateSelectedListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (listener != null) {
            listener.onDateSelected(year, month, day);
        }
    }
}
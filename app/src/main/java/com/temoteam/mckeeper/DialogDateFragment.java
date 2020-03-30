package com.temoteam.mckeeper;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DialogDateFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        setDate(year, month, day);

    }

    private void setDate(int year, int month, int day) {
        EditText editDate = getActivity().findViewById(R.id.editDate);
        month++;
        String dayStr;
        String monthStr;

        if (day < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = "" + day;
        }
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = "" + month;
        }
        editDate.setText(dayStr + "." + monthStr + "." + year);
        DialogFragment timeFragment = new DialogTimeFragment();

        timeFragment.show((getActivity()).getSupportFragmentManager(), "timePicker1");
    }
}

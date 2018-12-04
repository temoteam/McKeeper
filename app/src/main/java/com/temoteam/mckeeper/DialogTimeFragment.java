package com.temoteam.mckeeper;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class DialogTimeFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        setTime(hourOfDay, minute);
    }

    private void setTime(int hourOfDay, int minute) {

        EditText editTimeStart = getActivity().findViewById(R.id.editTimeStart);
        EditText editTimeEnd = getActivity().findViewById(R.id.editTimeEnd);

        if (editTimeStart.getText().toString().equals("")) {
            editTimeEnd.setEnabled(true);
            editTimeStart.setText(hourOfDay + ":" + minute);
            DialogFragment timeFragment = new DialogTimeFragment();
            timeFragment.show((getActivity()).getSupportFragmentManager(), "timePicker1");
        } else {
            if (editTimeEnd.getText().toString().equals("")) {
                editTimeEnd.setText(hourOfDay + ":" + minute);

            }
        }


    }


}
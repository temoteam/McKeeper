package com.temoteam.mckeeper;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

class DialogTimeFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @NonNull
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

        EditText editTimeStart = Objects.requireNonNull(getActivity()).findViewById(R.id.editTimeStart);
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
package com.temoteam.mckeeper;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddShiftFragment extends Fragment {


    private String date;

    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private int allHour;
    private int allMinute;

    private EditText editDate;
    private EditText startTime;
    private EditText endTime;
    private Switch isBreak;
    private Switch isPyatn;
    private RadioButton p1;
    private RadioButton p2;
    private TextView textAll;
    private Button recount;
    private Button save;


    public AddShiftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_shift, container, false);

    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getResources().getString(R.string.screen_add_shift));
        editDate = getView().findViewById(R.id.editDate);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateFragment = new DialogDateFragment();
                dateFragment.show(((MainActivity) getActivity()).getSupportFragmentManager(), "datePicker");
            }
        });

        startTime = getView().findViewById(R.id.editTimeStart);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timeFragment = new DialogTimeFragment();
                timeFragment.show(((MainActivity) getActivity()).getSupportFragmentManager(), "timePicker1");
            }
        });

        endTime = getView().findViewById(R.id.editTimeEnd);
        endTime.setEnabled(false);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timeFragment = new DialogTimeFragment();
                timeFragment.show(((MainActivity) getActivity()).getSupportFragmentManager(), "timePicker1");
            }
        });

        isBreak = getView().findViewById(R.id.switchBreak);
        isPyatn = getView().findViewById(R.id.switchPyatn);
        p1 = getView().findViewById(R.id.radioButton);
        p2 = getView().findViewById(R.id.radioButton2);
        textAll = getView().findViewById(R.id.textAll);
        recount = getView().findViewById(R.id.recount);
        save = getView().findViewById(R.id.buttonConfirm);

        DialogFragment dateFragment = new DialogDateFragment();
        dateFragment.show(((MainActivity) getActivity()).getSupportFragmentManager(), "datePicker");


        recount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doRecount(1);
                    }
                }
        );

        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doRecount(2);
                        ((MainActivity) getActivity()).onNavigationItemSelected(1);


                    }
                }
        );

    }


    private void doRecount(int i) {
        if (!(editDate.getText().toString().equals("")) && !(startTime.getText().toString().equals("")) && !(endTime.getText().toString().equals(""))) {

            date = editDate.getText().toString();
            startHour = Integer.parseInt(startTime.getText().toString().split(":")[0]);
            startMinute = Integer.parseInt(startTime.getText().toString().split(":")[1]);
            endHour = Integer.parseInt(endTime.getText().toString().split(":")[0]);
            endMinute = Integer.parseInt(endTime.getText().toString().split(":")[1]);
            allHour = endHour - startHour;

            if (allHour < 0) {
                allHour = 24 + allHour;
            }

            allMinute = endMinute - startMinute;

            if (allMinute < 0) {
                allHour--;
                allMinute = 60 + allMinute;
            }

            if (isBreak.isChecked()) {
                allMinute = allMinute - 30;
            }

            if (allMinute < 0) {
                allHour--;
                allMinute = 60 + allMinute;
            }

            if (isPyatn.isChecked()) {
                if (p1.isChecked()) {
                    allMinute = allMinute - 15;
                }

                if (p2.isChecked()) {
                    allMinute = allMinute - 30;
                }
            }

            if (allMinute < 0) {
                allHour--;
                allMinute = 60 + allMinute;
            }

            SharedPreferences myPreferences
                    = PreferenceManager.getDefaultSharedPreferences(getActivity());
            Date date1 = new Date();
            String nowMonth = date1.toString().split(" ")[1];
            float hours = ((StartActivity) getActivity()).getAllHours(nowMonth);
            int resID = getResources().getIdentifier(nowMonth, "integer", getActivity().getPackageName());

            double rate = (double) myPreferences.getInt("rate", 185) / 60;
            int allTime = allMinute + allHour * 60;
            float allMoney = 0;
            float pererabotki = 0;
            if (hours <= (getResources().getInteger(resID)) * 60) {
                allMoney = (Float.parseFloat(allTime * rate * 0.87 + ""));
            }

            if (hours > (getResources().getInteger(resID)) * 60) {
                allMoney = Float.parseFloat(allTime * rate * 0.87 + "");
                if (hours - (getResources().getInteger(resID)) <= 2) {
                    pererabotki = Float.parseFloat(allMoney * 0.5 + "");
                }

                if (hours - (getResources().getInteger(resID)) > 2) {
                    pererabotki = allMoney;
                }
            }

            Log.e("per", pererabotki + "");

            textAll.setText(date + " вы отработали " + allHour + " часов " + allMinute + " минут. И Заработали " + allMoney + " р.");

            if (i == 2) {
                ((StartActivity) getActivity()).insertData(date, allTime, allMoney, pererabotki);
            }
        } else {
            Toast toast = Toast.makeText(getActivity(),
                    "Empty fields", Toast.LENGTH_SHORT);
            toast.show();
        }

    }


}

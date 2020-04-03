package com.temoteam.mckeeper;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.txusballesteros.widgets.FitChart;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

    private int hours;

    private float money11;
    private float money26;
    private float pererabotki;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.screen_start));

        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Date date = new Date();
        String nowMonth = date.toString().split(" ")[1];
        hours = ((StartActivity) getActivity()).getAllHours(nowMonth);
        money11 = ((StartActivity) getActivity()).getAllMoney(nowMonth, 11);
        money26 = ((StartActivity) getActivity()).getAllMoney(nowMonth, 26);
        float pererabotki = ((StartActivity) getActivity()).getAllPererabotki(nowMonth);
        Button addShift = getView().findViewById(R.id.addShift);
        addShift.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getActivity()).onNavigationItemSelected(2);
                    }
                }
        );


        TextView textHours = getView().findViewById(R.id.textHours);
        TextView textMoneyAv = getView().findViewById(R.id.textMoneyAv);
        TextView textMoneyZp = getView().findViewById(R.id.textMoneyZp);
        TextView tv = getView().findViewById(R.id.textOverView);
        int resID = getResources().getIdentifier(nowMonth, "integer", getActivity().getPackageName());
        //getActivity().getResources(R.integer.Jan);

        if ((getResources().getInteger(resID)) - hours / 60 > 0) {
            textHours.setText("В " + nowMonth + " вы отработали:" + hours / 60 + " часов и " + hours % 60 + " минут. До переработок еще " + (getResources().getInteger(resID) - hours / 60) + " часов.");
        } else {
            textHours.setText("В " + nowMonth + " вы отработали:" + hours / 60 + " часов и " + hours % 60 + " минут. Из них переработок " + (getResources().getInteger(resID) - hours / 60) * (-1) + "часов.");
        }

        final FitChart fitChart = (FitChart) getActivity().findViewById(R.id.fitChart);
        fitChart.setMinValue(1);
        fitChart.setMaxValue(getResources().getInteger(resID) * 60);
        fitChart.setValue(hours);

        Log.d("lal", "Max" + fitChart.getMaxValue() + "Min" + fitChart.getMinValue() + "Now" + hours);
        tv.setText(hours / 60 + "/" + getResources().getInteger(resID));
        textMoneyAv.setText("В аванс придет: " + money26);
        textMoneyZp.setText("В зарплату придет: " + (money11 + pererabotki));
    }


}

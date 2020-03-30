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

import androidx.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class InitialFragment extends Fragment {
    private EditText editRest;
    private EditText editRate;
    private EditText editNumber;

    public InitialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_initial, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.screen_initial));
        //https://www.mcfamily.ru/c/portal/restaurants/list?locale=ru_RU
        Log.i("kek", "111");

        editRest = view.findViewById(R.id.editRest);
        editRate = view.findViewById(R.id.editRate);
        editNumber = view.findViewById(R.id.editNumber);
        Button save = view.findViewById(R.id.buttonSaveInitial);


        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!(editRest.getText().toString().equals("")) && !(editRate.getText().toString().equals("")) && !(editNumber.getText().toString().equals(""))) {
                            String rest = editRest.getText().toString();
                            String rate = editRate.getText().toString();
                            String number = editNumber.getText().toString();
                            int rateInt = Integer.parseInt(rate);
                            int numberInt = Integer.parseInt(number);
                            SharedPreferences myPreferences
                                    = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor myEditor = myPreferences.edit();
                            myEditor.putString("rest", rest);
                            myEditor.putInt("rate", rateInt);
                            myEditor.putInt("number", numberInt);
                            myEditor.apply();


                        }
                        ((MainActivity) getActivity()).onNavigationItemSelected(1);
                    }
                }
        );
    }

}

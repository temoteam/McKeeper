package com.temoteam.mckeeper;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;


public class InfoFragment extends Fragment {


    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);


    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle(getResources().getString(R.string.screen_info));
        Button mail = view.findViewById(R.id.button_mail);
        Button tg = view.findViewById(R.id.button_tg);
        Button vk = view.findViewById(R.id.button_vk);


        mail.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        email();
                    }
                }
        );

        tg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tg();
                    }
                }
        );

        vk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vk();
                    }
                }
        );
    }

    private void email() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:nesenenko@temoteam.ru"));
        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            //TODO: Handle case where no email app is available
        }
    }

    private void vk() {
        String url = "https://vk.com/id241199506";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void tg() {
        String url = "https://t.me/lohness";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


}

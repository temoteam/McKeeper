package com.temoteam.mckeeper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class PdfShowWithGoogle extends Fragment {

    public PdfShowWithGoogle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pdf_show_with_google, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        String link = Objects.requireNonNull(getArguments()).getString("link");
        WebView pdfWebView = Objects.requireNonNull(getActivity()).findViewById(R.id.pdfWebView);
        pdfWebView.getSettings().setJavaScriptEnabled(true);
        final String doc = "https://docs.google.com/viewer?url=" + link;
        //doc = "https://vk.com";
        pdfWebView.loadUrl(doc);
        pdfWebView.reload();


        pdfWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(doc);



                return true;
            }
        });
    }
}

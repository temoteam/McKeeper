package com.temoteam.mckeeper;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.charset.StandardCharsets;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private static final JSONParser parser = new JSONParser();
    private String[] titles;
    private String[] links;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    private static String fixEncoding(String response) {
        byte[] u = response.getBytes(
                StandardCharsets.ISO_8859_1);
        response = new String(u, StandardCharsets.UTF_8);
        return response;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle(getResources().getString(R.string.screen_news));

        try {
            getNews();
        } catch (Exception e) {
            Log.d("kek", e.toString());
        }
        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


    }

    private void getNews() {
        String url = "https://home.temoteam.ru/mcd/news/news.json";

        RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        String utf8String = fixEncoding(response);

                        Log.d("test", utf8String);

                        Object obj = null;
                        try {
                            obj = parser.parse(utf8String);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        JSONObject jsonObject = (JSONObject) obj;
                        JSONArray jsonArray = (JSONArray) Objects.requireNonNull(jsonObject).get("array");
                        titles = new String[Objects.requireNonNull(jsonArray).size()];
                        links = new String[jsonArray.size()];
                        Log.d("kek", jsonArray.size() + "");
                        for (int i = 0; i < jsonArray.size(); i++) {
                            titles[i] = (String) ((JSONObject) jsonArray.get(i)).get("name");
                            links[i] = (String) ((JSONObject) jsonArray.get(i)).get("link");
                        }

                        mAdapter = new com.temoteam.mckeeper.Adapters.NewsAdapter(titles, links, getActivity());
                        recyclerView.setAdapter(mAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test", error.toString());
            }
        });
        queue.add(stringRequest);


    }

}

package com.temoteam.mckeeper;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    public static JSONParser parser = new JSONParser();
    protected String[] mDataset;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.screen_news));
        try {
            getNews();
        } catch (Exception e) {
            Log.d("kek", e.toString());
        }

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new com.temoteam.mckeeper.Adapters.NewsAdapter(mDataset);
        recyclerView.setAdapter(mAdapter);

    }

    public void getNews() throws Exception {
        String news_url = "http://home.temoteam.ru/mcd/news/news.json";
        String feed = "{\n" +
                "  \"array\": [\n" +
                "    {\n" +
                "      \"name\": \"Выпуск 10 - 11 марта\",\n" +
                "      \"link\": \"http://home.temoteam.ru/mcd/news/Crew_News_10_2020_11.03.pdf\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Выпуск 11 - 18 марта\",\n" +
                "      \"link\": \"http://home.temoteam.ru/mcd/news/Crew_News_11_2020_18.03.pdf\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Выпуск 12 - 24 марта\",\n" +
                "      \"link\": \"http://home.temoteam.ru/mcd/news/Crew_News_12_2020_24.03.pdf\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Object obj = parser.parse(feed);
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray jsonArray = (JSONArray) jsonObject.get("array");
        mDataset = new String[jsonArray.size()];
        Log.d("kek", jsonArray.size() + "");
        for (int i = 0; i < jsonArray.size(); i++) {
            mDataset[i] = (String) ((JSONObject) jsonArray.get(i)).get("name");
        }

    }


}

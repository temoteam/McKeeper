package com.temoteam.mckeeper.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.temoteam.mckeeper.PdfShowWithGoogle;
import com.temoteam.mckeeper.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private static final String TAG = "CustomAdapter";
    private static String[] links;
    private String[] titles;
    private static Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsAdapter(String[] myDataset, String[] links, Context context) {
        titles = myDataset;
        this.links = links;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.getTextView().setText(titles[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return titles.length;
    }

    private static void showNewsPdf(String link) {
        Fragment pdf = new PdfShowWithGoogle();
        Bundle bundle = new Bundle();
        bundle.putString("link", link);
        pdf.setArguments(bundle);
        android.app.FragmentTransaction fTrans;
        fTrans = ((Activity) context).getFragmentManager().beginTransaction();
        fTrans.replace(R.id.container, pdf);
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private final TextView textView;

        public MyViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + links[getAdapterPosition()] + " clicked.");
                    showNewsPdf(links[getAdapterPosition()]);
                }
            });
            textView = (TextView) v.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}

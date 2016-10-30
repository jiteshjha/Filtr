package com.jitesh.filtr;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Siddharth Kothiyal on 27-10-2016.
 * Fragment to load the layout of RecyclerView on to the Relative Layout in activity_main.xml
 * and to perform any actions such as loading adapters and adding eventListeners etc
 *
 */

public class FilterFragment extends Fragment {

    String imageLink;
    Context context;
    Uri uri;
    onRecyclerViewPress onRecyclerViewPress;
    RecyclerView recyclerView;
    String[] filterOptions = {"Sepia", "Sky Blue", "Lawn Green", "Violet" , "1970", "Flamingo", "B&W"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_fragment, container, false);

        ArrayList<Filter> listFilters = new ArrayList<>();
        for (String x : filterOptions) {
            Filter filter = new Filter();
            filter.setFilterName(x);
            listFilters.add(filter);
        }


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMain);
        ImageAdapter imageAdapter = new ImageAdapter(context, listFilters, imageLink, new ImageAdapter.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {

                if(position == 0){

                    uri = Uri.parse(imageLink); // parsing String into Uri, load the uri
                    Toast.makeText(context, "FUCK!", Toast.LENGTH_SHORT).show();
                    // make changes on to the bitmap
                    onRecyclerViewPress.setUri(uri); // after changes are made, the uri is sent to the mainActivity from here, so that from there it can call the ImageFragment and reload it
                }
            }
        });
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

        return view;
    }

    public void getArgs(String image, Context con){ // get arguments from main activity
        context = con;
        imageLink = image;
    }

    public interface onRecyclerViewPress { // interface definition
        public void setUri(Uri uri);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onRecyclerViewPress = (onRecyclerViewPress) activity;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

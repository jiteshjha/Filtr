package com.jitesh.filtr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Uri saveUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Uri uri = Uri.parse(intent.getStringExtra("imageUri"));
        saveUri = uri;
	    String imageLink = intent.getStringExtra("imageUri");
        ImageView imageView = (ImageView)findViewById(R.id.imageView);

        imageView.setImageURI(uri);

        String[] someString = {"Filter 1", "Filter 2", "Filter 3", "Filter 4", "Filter 5", "Filter 6", "Filter 7"};
        ArrayList<Filter> listFilters = new ArrayList<>();
        for (String x : someString) {
            Filter filter = new Filter();
            filter.setFilterName(x);
            listFilters.add(filter);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain);
        ImageAdapter imageAdapter = new ImageAdapter(MainActivity.this, listFilters, imageLink);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
    }

    public void saveAction(View v) {
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        Intent intent = new Intent(getApplicationContext(), SaveActivity.class);
        intent.putExtra("imageUri", saveUri.toString());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:

                return true;
            case R.id.menu_about:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

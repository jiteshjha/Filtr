package com.jitesh.filtr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements FilterFragment.onRecyclerViewPress {

    String imageLink;
    Uri saveUri;

    // String[] filterOptions = {"Sepia", "1970", "Flamingo", "B&W"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Uri uri = Uri.parse(intent.getStringExtra("imageUri"));
        saveUri = uri;
        imageLink = intent.getStringExtra("imageUri");
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FilterFragment filterFragment = new FilterFragment();
        filterFragment.getArgs(imageLink, MainActivity.this);
        ImageFragment imageFragment = new ImageFragment();
        imageFragment.getURI(saveUri);
        fragmentTransaction.add(R.id.filterFrag, filterFragment);
        fragmentTransaction.add(R.id.imageFrag, imageFragment);
        fragmentTransaction.commit();
        //ImageView imageView = (ImageView)findViewById(R.id.imageView);

        //imageView.setImageURI(uri);


    /*    ArrayList<Filter> listFilters = new ArrayList<>();
        for (String x : filterOptions) {
            Filter filter = new Filter();
            filter.setFilterName(x);
            listFilters.add(filter);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain);
        ImageAdapter imageAdapter = new ImageAdapter(MainActivity.this, listFilters, imageLink);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
    */
    }

    public void saveAction(View v) {
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

    public void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Created by Jitesh Kumar Jha")
                .setTitle("About");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                showAboutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void setUri(Uri uri) { //interface from FilterFragment, reloading the ImageFragment here
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ImageFragment imageFragment = new ImageFragment();
        saveUri = uri;
        imageFragment.getURI(saveUri);
        fragmentTransaction.add(R.id.imageFrag, imageFragment);
        fragmentTransaction.commit();
    }
}
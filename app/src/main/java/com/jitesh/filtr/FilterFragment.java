package com.jitesh.filtr;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
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
    //String[] filterOptions = {"Sepia", "Sky Blue", "Lawn Green", "Violet" , "1970", "Flamingo", "B&W"};
    String[] filterOptions = {"Sepia", "Sky Blue", "Lawn Green", "Violet", "Original"};
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_fragment, container, false);

        ArrayList<Filter> listFilters = new ArrayList<>();
        for (String x : filterOptions) {
            Filter filter = new Filter();
            filter.setFilterName(x);
            listFilters.add(filter);
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMain);
        ImageAdapter imageAdapter = new ImageAdapter(context, listFilters, imageLink);
        SharedPreferences.Editor editor_out = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor_out.putString("uri", Uri.parse(imageLink).toString());
        recyclerView.setAdapter(imageAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {


                        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
                        editor.clear();
                        try {
                            if (position == 0) {
                                Toast.makeText(context, "Sepia",Toast.LENGTH_SHORT ).show();
                                uri = Uri.parse(imageLink);
                                Bitmap b = getBitmapFromUri(uri);
                                Bitmap sepia_image = createSepiaToningEffect(b, 150, 0.7, 0.2, 0.1);
                                Uri imageURI = getImageUri(context, sepia_image);

                                FragmentManager fm = getFragmentManager();
                                ImageFragment imageFragment = (ImageFragment)fm.findFragmentById(R.id.imageFrag);
                                imageFragment.setURI(imageURI);
                                editor.putString("uri", imageURI.toString());
                            }
                            else if(position == 1) {
                                uri = Uri.parse(imageLink);
                                Bitmap b = getBitmapFromUri(uri);
                                Bitmap image = applyShadingFilter(b, Color.parseColor("#bbdefb"));
                                Uri imageURI = getImageUri(context, image);

                                FragmentManager fm = getFragmentManager();
                                ImageFragment imageFragment = (ImageFragment)fm.findFragmentById(R.id.imageFrag);
                                imageFragment.setURI(imageURI);
                                editor.putString("uri", imageURI.toString());
                            }
                            else if(position == 2) {
                                uri = Uri.parse(imageLink);
                                Bitmap b = getBitmapFromUri(uri);
                                Bitmap image = applyShadingFilter(b, Color.parseColor("#c5e1a5"));
                                Uri imageURI = getImageUri(context, image);

                                FragmentManager fm = getFragmentManager();
                                ImageFragment imageFragment = (ImageFragment)fm.findFragmentById(R.id.imageFrag);
                                imageFragment.setURI(imageURI);
                                editor.putString("uri", imageURI.toString());
                            }
                            else if(position == 3) {
                                uri = Uri.parse(imageLink);
                                Bitmap b = getBitmapFromUri(uri);
                                Bitmap image = applyShadingFilter(b, Color.parseColor("#E066FF"));
                                Uri imageURI = getImageUri(context, image);

                                FragmentManager fm = getFragmentManager();
                                ImageFragment imageFragment = (ImageFragment)fm.findFragmentById(R.id.imageFrag);
                                imageFragment.setURI(imageURI);
                                editor.putString("uri", imageURI.toString());
                            }
                            else if(position == 4) {
                                uri = Uri.parse(imageLink);

                                FragmentManager fm = getFragmentManager();
                                ImageFragment imageFragment = (ImageFragment)fm.findFragmentById(R.id.imageFrag);
                                imageFragment.setURI(uri);
                                editor.putString("uri", uri.toString());
                            }
                            editor.commit();
                        } catch (IOException
                                e) {}

                    }
                })
        );
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

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    // ---Image Effects

    public static Bitmap createSepiaToningEffect(Bitmap src, int depth, double red, double green, double blue) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // constant grayscale
        final double GS_RED = 0.3;
        final double GS_GREEN = 0.59;
        final double GS_BLUE = 0.11;
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                // get color on each channel
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // apply grayscale sample
                B = G = R = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);

                // apply intensity level for sepid-toning on each channel
                R += (depth * red);
                if(R > 255) { R = 255; }

                G += (depth * green);
                if(G > 255) { G = 255; }

                B += (depth * blue);
                if(B > 255) { B = 255; }

                // set new pixel color to output image
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

    public static Bitmap applyShadingFilter(Bitmap source, int shadingColor) {
        // get image size
        int width = source.getWidth();
        int height = source.getHeight();
        int[] pixels = new int[width * height];
        // get pixel array from source
        source.getPixels(pixels, 0, width, 0, 0, width, height);

        int index = 0;
        // iteration through pixels
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                // get current index in 2D-matrix
                index = y * width + x;
                // AND
                pixels[index] &= shadingColor;
            }
        }
        // output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bmOut;
    }
}

package com.jitesh.filtr;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ImageFragment extends Fragment {

    ImageView imageView;
    Uri imageLink;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.image_fragment, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageURI(imageLink);
        return view;
    }



    public void getURI(Uri uri){
        imageLink = uri;
    }
}

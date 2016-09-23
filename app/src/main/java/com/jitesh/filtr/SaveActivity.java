package com.jitesh.filtr;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        Intent intent = getIntent();
        Uri uri = Uri.parse(intent.getStringExtra("imageUri"));
        ImageView imageView = (ImageView)findViewById(R.id.imageView2);

        imageView.setImageURI(uri);
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @SuppressWarnings( "deprecation" )
//    public static Intent shareImage(Context context, String pathToImage) {
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
//            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//        else
//            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
//
//        shareIntent.setType("image/*");
//
//        // For a file in shared storage.  For data in private storage, use a ContentProvider.
//        Uri uri = Uri.fromFile(context.getFileStreamPath(pathToImage));
//        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//        return shareIntent;
//    }


}

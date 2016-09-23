package com.jitesh.filtr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ImagePickActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE_GALLERY = 1;
    private static final int SELECT_PICTURE_CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pick);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        Button button3 = (Button)findViewById(R.id.button3);
        Button button2 = (Button)findViewById(R.id.button2);
        button3.setTypeface(font);
        button2.setTypeface(font);
    }

    public void fetchGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE_GALLERY);
    }

    public void fetchCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, SELECT_PICTURE_CAMERA);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            if (requestCode == SELECT_PICTURE_GALLERY) {
                Uri uri = data.getData();
                //Toast.makeText(getApplicationContext(), "This works!", Toast.LENGTH_SHORT).show();
                intent.putExtra("imageUri", uri.toString());
            }
            else if(requestCode == SELECT_PICTURE_CAMERA) {
                Uri uri = data.getData();
                intent.putExtra("imageUri", uri.toString());

            }
            startActivity(intent);
        }
    }
}

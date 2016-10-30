package com.jitesh.filtr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.VH> {


    private LayoutInflater inflater;
    List<Filter> filterList = Collections.emptyList();
    Context context;
    String imageLink;
    ImageView imageView;

    public ImageAdapter(Context context, List<Filter> filterList, String imageLink) {
        inflater = LayoutInflater.from(context);
        this.filterList = filterList;
        this.context = context;
        this.imageLink = imageLink;
    }

    @Override
    public ImageAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.filter_box, parent, false);
        imageView = (ImageView) parent.findViewById(R.id.imageView);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.VH holder, int position) {
        final Filter filter = filterList.get(position);


        Uri uri = Uri.parse(imageLink);

        try {
            Bitmap b = getBitmapFromUri(uri);

            //Scale down Bitmap
            Matrix m = new Matrix();
            m.setRectToRect(new RectF(0, 0, b.getWidth(), b.getHeight()), new RectF(0, 0, 120, 120), Matrix.ScaleToFit.CENTER);
            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);

            if(filter.getFilterName().equalsIgnoreCase("Sepia")) {
                Bitmap sepia_image = createSepiaToningEffect(b, 150, 0.7, 0.2, 0.1);
                holder.imageSet.setImageBitmap(sepia_image);
            }
            else {
                holder.imageSet.setImageBitmap(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




        holder.filterName.setText(filter.getFilterName());
        final String filterName = filter.getFilterName();
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), filterName, Toast.LENGTH_SHORT).show();
                if(filterName.equalsIgnoreCase("Sepia")) {
                    //ImageView imageView = (ImageView) v.getParent().findViewById(R.id.imageView);
                    Uri uri = Uri.parse(imageLink);
                    try {
                        Bitmap sepia_image = createSepiaToningEffect(getBitmapFromUri(uri), 150, 0.7, 0.2, 0.1);
                        BitmapDrawable drawable = new BitmapDrawable(v.getResources(), sepia_image);
                        //imageView.setImageDrawable(drawable);
                        //imageView.setImageURI(getImageUri(v.getContext(), sepia_image));
                        if(imageView == null) {
                            Toast.makeText(v.getContext(), "fuck you", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(v.getContext(), filterName, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView imageSet;
        TextView filterName;
        RelativeLayout relativeLayout;

        public VH(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.boxBackground);
            filterName = (TextView) itemView.findViewById(R.id.filterName);
            imageSet = (ImageView) itemView.findViewById(R.id.loadImage);
        }
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

}


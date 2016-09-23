package com.jitesh.filtr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.VH> {


    private LayoutInflater inflater;
    List<Filter> filterList = Collections.emptyList();
    Context context;
    String imageLink;

    public ImageAdapter(Context context, List<Filter> filterList, String imageLink) {
        inflater = LayoutInflater.from(context);
        this.filterList = filterList;
        this.context = context;
        this.imageLink = imageLink;
    }

    @Override
    public ImageAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.filter_box, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.VH holder, int position) {
        final Filter filter = filterList.get(position);


        Uri uri = Uri.parse(imageLink);
        try {
            holder.imageSet.setImageBitmap(getBitmapFromUri(uri));
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.filterName.setText(filter.getFilterName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add func
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

}

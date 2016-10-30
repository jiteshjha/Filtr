package com.jitesh.filtr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.VH> implements RecyclerView.OnItemTouchListener {

    GestureDetector mGestureDetector;
    private OnItemClickListener mListener;
    private LayoutInflater inflater;
    List<Filter> filterList = Collections.emptyList();
    Context context;
    String imageLink;

    public ImageAdapter(Context context, List<Filter> filterList, String imageLink, OnItemClickListener listener) {
        mListener = listener;
        inflater = LayoutInflater.from(context);
        this.filterList = filterList;
        this.context = context;
        this.imageLink = imageLink;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { //to detect which item was pressed in recyclerview
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
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



    public interface OnItemClickListener {   //interface to detect touches
        public void onItemClick(View view, int position);

    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}

}


package org.pcov.pcovannouncements.Adapters;

import android.content.Context;
import android.provider.Contacts;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;

import org.pcov.pcovannouncements.R;
import org.pcov.pcovannouncements.UniversalImageLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private static final String TAG = "GalleryAdapter";
    private static final int NUM_GRID_COLUMNS = 3;

    private ArrayList<Gallery> mPhotos;
    private Context mContext;

    public class GalleryViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public GalleryViewHolder(@NonNull View itemView, ImageView postImage) {
            super(itemView);
            RecyclerView recyclerView;
            image = postImage;
            recyclerView = (RecyclerView) itemView.findViewById(R.id.galleryRecyclerView);
            int gridWidth = mContext.getResources().getDisplayMetrics().widthPixels;
            int imageWidth = gridWidth/NUM_GRID_COLUMNS;
            image.setMaxHeight(imageWidth);
            image.setMaxWidth(imageWidth);
        }
    }

    public GalleryAdapter(Context mContext, ArrayList list) {
        this.mContext = mContext;
        this.mPhotos = list;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_gallery, parent, false  );
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, final int position) {
        UniversalImageLoader.setImage(mPhotos.get(position).ge, holder.image);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

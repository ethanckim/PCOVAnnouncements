package org.pcov.pcovannouncements.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.pcov.pcovannouncements.DataClass.ImageCard;
import org.pcov.pcovannouncements.R;

import java.util.ArrayList;
import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    private static final String TAG = "GalleryAdapter";
    private static final int NUM_GRID_COLUMNS = 3;

    private Context mContext;
    private List<ImageCard> images;
    private GalleryAdapter.OnCardClickListener mListener;

    public GalleryAdapter(Context mContext, ArrayList<ImageCard> list) {
        this.mContext = mContext;
        this.images = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_images, parent, false  );
        GalleryAdapter.MyViewHolder viewHolder = new GalleryAdapter.MyViewHolder(view, mListener);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int position) {
        ImageCard uploadCurrent = images.get(position);
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(viewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder     {
        public ImageView mImageView;

        public MyViewHolder(@NonNull View itemView, final OnCardClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.galleryImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onCardClick(position);
                        }
                    }
                }
            });
        }
    }


    public interface OnCardClickListener {
        //Use this method to send the position of the clicked card for the fragment.
        void onCardClick(int position);
    }

    public void setOnClickListener(GalleryAdapter.OnCardClickListener listener) {
        mListener = listener;
    }

}

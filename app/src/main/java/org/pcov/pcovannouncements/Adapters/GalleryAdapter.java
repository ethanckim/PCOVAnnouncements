package org.pcov.pcovannouncements.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.pcov.pcovannouncements.DataClass.ImageCard;
import org.pcov.pcovannouncements.R;

import java.util.ArrayList;
import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private static final String TAG = "GalleryAdapter";
    private static final int NUM_GRID_COLUMNS = 3;

    private Context mContext;
    List<ImageCard> images;
    private GalleryAdapter.OnCardClickListener mListener;

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImage;
        public TextView mText;

        public GalleryViewHolder(View itemView, List<ImageCard> imgArray, Context mContext) {
            super(itemView);
            mImage = itemView.findViewById(R.id.galleryImageView);
            mText = itemView.findViewById(R.id.galleryTextView);
            RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.galleryRecyclerView);
            int gridWidth = mContext.getResources().getDisplayMetrics().widthPixels;
            int imageWidth = gridWidth/NUM_GRID_COLUMNS;

        }
    }

    public GalleryAdapter(Context mContext, ArrayList<ImageCard> list) {
        this.mContext = mContext;
        this.images = list;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_images, parent, false  );
        return new GalleryViewHolder(view, images, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryAdapter.GalleryViewHolder viewHolder, final int position) {
        ImageCard currentCard = images.get(position);
        viewHolder.mText.setText(currentCard.getmImageText());
        viewHolder.mImage.setImageResource(currentCard.getmImageId());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public interface OnCardClickListener {
        //Use this method to send the position of the clicked card for the fragment.
        void onCardClick(int position);
    }

    public void setOnClickListener(GalleryAdapter.OnCardClickListener listener) {
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public MyViewHolder(@NonNull View itemView, final OnCardClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.galleryImageView);
            mTextView = itemView.findViewById(R.id.galleryTextView);

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

}

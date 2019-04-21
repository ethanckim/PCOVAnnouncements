package org.pcov.pcovannouncements.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import org.pcov.pcovannouncements.R;
import org.pcov.pcovannouncements.SermonCard;

import java.util.ArrayList;

public class SermonCardAdapter extends RecyclerView.Adapter<SermonCardAdapter.MyViewHolder> {

    private ArrayList<SermonCard> mSermonCardList;
    private OnCardClickListener mListener;

    //Use interface to detect click
    public interface OnCardClickListener {
        //Use this method to send the position of the clicked card for the fragment.
        void onCardClick(int position);
    }

    public void setOnClickListener(OnCardClickListener listener) {
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public YouTubeThumbnailView mYouTubeThumbnailView;
        public TextView mTitleTextView;
        public TextView mSubtitleTextView;

        public MyViewHolder(@NonNull View itemView, final OnCardClickListener listener) {
            super(itemView);
            mYouTubeThumbnailView = itemView.findViewById(R.id.sermon_card_imageview);
            mTitleTextView = itemView.findViewById(R.id.sermon_title_textview);
            mSubtitleTextView = itemView.findViewById(R.id.sermon_subtitle_textview);

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

    public SermonCardAdapter(ArrayList<SermonCard> sermonCardList) {
        mSermonCardList = sermonCardList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_videos, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(v, mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, int i) {
        SermonCard currentCard = mSermonCardList.get(i);
        final String youtubeThumnailLink = currentCard.getmYoutubeThumnailLink();

        YouTubeThumbnailView.OnInitializedListener mOnInitializedListener;
        mOnInitializedListener = new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(youtubeThumnailLink);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                Log.w("WARNING","Failed to initialize the youtube thumbnail");
            }
        };

        viewHolder.mYouTubeThumbnailView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
        viewHolder.mTitleTextView.setText(currentCard.getmTitleText());
        viewHolder.mSubtitleTextView.setText(currentCard.getmSubtitleText());
    }

    @Override
    public int getItemCount() {
        return mSermonCardList.size();
    }
}

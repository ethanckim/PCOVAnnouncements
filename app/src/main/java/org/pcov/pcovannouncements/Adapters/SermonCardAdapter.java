package org.pcov.pcovannouncements.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import org.pcov.pcovannouncements.R;
import org.pcov.pcovannouncements.DataClass.SermonCard;

import java.util.ArrayList;

public class SermonCardAdapter extends RecyclerView.Adapter<SermonCardAdapter.MyViewHolder> {

    private static final String TAG = SermonCardAdapter.class.getSimpleName();
    private ArrayList<SermonCard> mSermonCardList;
    private OnCardClickListener mListener;


    public SermonCardAdapter(ArrayList<SermonCard> sermonCardList) {
        mSermonCardList = sermonCardList;
    }

    public void setOnClickListener(OnCardClickListener listener) {
        mListener = listener;
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
        final SermonCard currentCard = mSermonCardList.get(i);
        final String youtubeThumnailLink = currentCard.getmVideoId();

        viewHolder.mTitleTextView.setText(currentCard.getmTitleText());

        YouTubeThumbnailView.OnInitializedListener mOnInitializedListener;
        mOnInitializedListener = new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(youtubeThumnailLink);
                viewHolder.initialized = false;

                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        //print or show error when thumbnail load failed
                        Log.e(TAG, "Youtube Thumbnail Error");
                        youTubeThumbnailLoader.release();

                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //print or show error when initialization failed
                Log.e(TAG, "Youtube Initialization Failure");
            }
        };
        if (!viewHolder.initialized) {
            viewHolder.initialized = true;
            viewHolder.mYouTubeThumbnailView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
        }
    }

    @Override
    public int getItemCount() {
        return mSermonCardList.size();
    }

    //Use interface to detect click
    public interface OnCardClickListener {
        //Use this method to send the position of the clicked card for the fragment.
        void onCardClick(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public YouTubeThumbnailView mYouTubeThumbnailView;
        public TextView mTitleTextView;
        public boolean initialized = false;

        public MyViewHolder(@NonNull View itemView, final OnCardClickListener listener) {
            super(itemView);
            mYouTubeThumbnailView = itemView.findViewById(R.id.sermon_card_imageview);
            mTitleTextView = itemView.findViewById(R.id.sermon_title_textview);

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

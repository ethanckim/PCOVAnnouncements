package org.pcov.pcovannouncements.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.pcov.pcovannouncements.DataClass.NewsCard;
import org.pcov.pcovannouncements.R;

import java.util.ArrayList;

public class NewsCardAdapter extends RecyclerView.Adapter<NewsCardAdapter.MyViewHolder> {

    private ArrayList<NewsCard> mNewsCardList;
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
        public ImageView mImageView;
        public TextView mTitleTextView;
        public TextView mDateTextView;

        public MyViewHolder(@NonNull View itemView, final OnCardClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.newsCardImageView);
            mTitleTextView = itemView.findViewById(R.id.newsTitleTextView);
            mDateTextView = itemView.findViewById(R.id.newsDateTextView);

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

    public NewsCardAdapter(ArrayList<NewsCard> newsCardList) {
        mNewsCardList = newsCardList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_news, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(v, mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        NewsCard currentCard = mNewsCardList.get(i);

        viewHolder.mImageView.setImageResource(currentCard.getmImageResource());
        viewHolder.mTitleTextView.setText(currentCard.getTitle());
        viewHolder.mDateTextView.setText(currentCard.getDate());
    }

    @Override
    public int getItemCount() {
        return mNewsCardList.size();
    }
}
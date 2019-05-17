package org.pcov.pcovannouncements.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pcov.pcovannouncements.AboutUsWebviewActivity;
import org.pcov.pcovannouncements.Adapters.InfoCardAdapter;
import org.pcov.pcovannouncements.Adapters.NewsCardAdapter;
import org.pcov.pcovannouncements.DataClass.InformationCard;
import org.pcov.pcovannouncements.DataClass.NewsCard;
import org.pcov.pcovannouncements.R;

import java.util.ArrayList;

public class AnnouncementFragment extends Fragment {

    private View v;
    private RecyclerView mRecyclerView;
    private ArrayList<NewsCard> mNewsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNewsList = new ArrayList<>();
        mNewsList.add(new NewsCard(R.drawable.ic_arrow_forward, "PLACEHOLDER1", "PLACEHOLDER1"));
        mNewsList.add(new NewsCard(R.drawable.ic_arrow_forward, "PLACEHOLDER2", "PLACEHOLDER2"));
        mNewsList.add(new NewsCard(R.drawable.ic_arrow_forward, "PLACEHOLDER3", "PLACEHOLDER3"));
        mNewsList.add(new NewsCard(R.drawable.ic_arrow_forward, "PLACEHOLDER4", "PLACEHOLDER4"));
        mNewsList.add(new NewsCard(R.drawable.ic_arrow_forward, "PLACEHOLDER5", "PLACEHOLDER5"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_announecment, container, false);

        mRecyclerView = v.findViewById(R.id.newsRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        NewsCardAdapter adapter = new NewsCardAdapter(mNewsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new NewsCardAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(int position) {
                //Navigate to the new activity, based off the card (Use position the distinguish cards)
                Intent i;
                i = new Intent(getActivity(), AboutUsWebviewActivity.class);
                i.putExtra("position", position);
                startActivity(i);

            }
        });

        return v;
    }
}
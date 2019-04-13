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
import org.pcov.pcovannouncements.InfoCardAdapter;
import org.pcov.pcovannouncements.InformationCard;
import org.pcov.pcovannouncements.R;
import org.pcov.pcovannouncements.VideoViewer;

import java.util.ArrayList;


public class VideosFragment extends Fragment {

    private View v;
    private RecyclerView mRecyclerView;
    private ArrayList<InformationCard> mSermonVideosList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSermonVideosList = new ArrayList<>();
        mSermonVideosList.add(new InformationCard(R.drawable.ic_arrow_forward, getString(R.string.about_us)));
        mSermonVideosList.add(new InformationCard(R.drawable.ic_arrow_forward, getString(R.string.for_newcomers)));
        mSermonVideosList.add(new InformationCard(R.drawable.ic_arrow_forward, getString(R.string.worship_services)));
        mSermonVideosList.add(new InformationCard(R.drawable.ic_arrow_forward, getString(R.string.church_history)));
        mSermonVideosList.add(new InformationCard(R.drawable.ic_arrow_forward, getString(R.string.visit_us)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_videos, container, false);

        mRecyclerView = v.findViewById(R.id.videosRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        InfoCardAdapter adapter = new InfoCardAdapter(mSermonVideosList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new InfoCardAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(int position) {
                //Navigate to the new activity, based off the card (Use position the distinguish cards)
                Intent i;
                i = new Intent(getActivity(), VideoViewer.class);
                i.putExtra("position", position);
                startActivity(i);

            }
        });

        return v;
    }

}

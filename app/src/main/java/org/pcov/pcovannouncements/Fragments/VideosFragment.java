package org.pcov.pcovannouncements.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pcov.pcovannouncements.R;


public class VideosFragment extends Fragment {

    private View v;
    private RecyclerView mRecyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_videos, container, false);

        mRecyclerView = v.findViewById(R.id.videosRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        return v;
    }

}

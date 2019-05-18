package org.pcov.pcovannouncements.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;

import org.pcov.pcovannouncements.Adapters.GalleryAdapter;
import org.pcov.pcovannouncements.Adapters.SermonCardAdapter;
import org.pcov.pcovannouncements.DataClass.SermonCard;
import org.pcov.pcovannouncements.MainActivity;
import org.pcov.pcovannouncements.R;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    private RecyclerView recyclerView;
    private View v;
    private ArrayList<Gallery> mGalleryList = new ArrayList<>();
    private GalleryAdapter madapter = new GalleryAdapter(mGalleryList);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.galleryRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(madapter);
        return v;
    }
}

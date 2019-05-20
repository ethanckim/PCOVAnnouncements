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
import android.widget.ImageView;

import org.pcov.pcovannouncements.Adapters.GalleryAdapter;
import org.pcov.pcovannouncements.Adapters.SermonCardAdapter;
import org.pcov.pcovannouncements.DataClass.ImageCard;
import org.pcov.pcovannouncements.DataClass.SermonCard;
import org.pcov.pcovannouncements.MainActivity;
import org.pcov.pcovannouncements.R;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    private RecyclerView recyclerView;
    private View v;
    private ArrayList<ImageCard> mGalleryList = new ArrayList<>();
    private GalleryAdapter madapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        madapter = new GalleryAdapter(getContext(), mGalleryList);

        mGalleryList.add(new ImageCard(R.drawable.pcov_churchphoto, "nothing"));
        mGalleryList.add(new ImageCard(R.drawable.pcov_churchphoto, "nothing"));
        mGalleryList.add(new ImageCard(R.drawable.pcov_churchphoto, "nothing"));
        mGalleryList.add(new ImageCard(R.drawable.pcov_churchphoto, "nothing"));
        mGalleryList.add(new ImageCard(R.drawable.pcov_churchphoto, "nothing"));



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.galleryRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(madapter);
        return v;
    }
}

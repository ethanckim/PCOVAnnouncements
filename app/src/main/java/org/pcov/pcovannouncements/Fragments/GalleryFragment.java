package org.pcov.pcovannouncements.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import org.pcov.pcovannouncements.Adapters.GalleryAdapter;
import org.pcov.pcovannouncements.DataClass.ImageCard;
import org.pcov.pcovannouncements.GalleryExtendActivity;
import org.pcov.pcovannouncements.R;
import org.pcov.pcovannouncements.Utils;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private GalleryAdapter madapter;

    private FirebaseStorage mStorage;
    private FirebaseFirestore mDatabase;

    private ArrayList<ImageCard> mGalleryList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStorage = FirebaseStorage.getInstance();
        mDatabase = FirebaseFirestore.getInstance();
        mGalleryList = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.galleryRecyclerView);
        recyclerView.setHasFixedSize(true);

        madapter = new GalleryAdapter(this.getContext(), mGalleryList);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }

        if (!Utils.isDeviceOnline(this.getActivity())) {
            Toast noInternetToast = Toast.makeText(getActivity().getApplicationContext(), getString(R.string.no_connection_gallery), Toast.LENGTH_LONG);
            noInternetToast.show();
        }

        recyclerView.setAdapter(madapter);
        //Get all the image references from the firestore.
        getListItems();

        madapter.setOnClickListener(new GalleryAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(int position) {
                //Navigate to the new activity, based off the card (Use position to distinguish cards)
                Intent i;
                i = new Intent(getActivity(), GalleryExtendActivity.class);
                i.putExtra("imageUrl", mGalleryList.get(position).getImageUrl());
                i.putExtra("imageTag", mGalleryList.get(position).getTag());
                startActivity(i);
            }
        });

        return v;
    }

    private void getListItems() {
        mDatabase.collection("Images")
                .orderBy("position", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("Firebase", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.
                            List<ImageCard> imageCards = documentSnapshots.toObjects(ImageCard.class);
                            // Add all to your list
                            mGalleryList.addAll(imageCards);
                            madapter.notifyDataSetChanged();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
            }
        });
    }
}

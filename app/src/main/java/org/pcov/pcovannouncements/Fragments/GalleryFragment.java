package org.pcov.pcovannouncements.Fragments;

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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.pcov.pcovannouncements.Adapters.GalleryAdapter;
import org.pcov.pcovannouncements.DataClass.ImageCard;
import org.pcov.pcovannouncements.DataClass.NewsCard;
import org.pcov.pcovannouncements.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private View v;
    private ArrayList<ImageCard> mGalleryList;
    private GalleryAdapter madapter;
    private StorageReference mStorageRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mStorageRef = FirebaseStorage.getInstance().getReference();
        mGalleryList = new ArrayList<>();

        getListItems();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.galleryRecyclerView);
        recyclerView.setHasFixedSize(true);

        madapter = new GalleryAdapter(getContext(), mGalleryList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(madapter);

        return v;
    }



    public void getListItems() {
//        File localFile = null;
//        try {
//            localFile = File.createTempFile("images", "jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        mStorageRef.getFile(localFile)
//                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                        List<ImageCard> imageCards = new ImageCard(, "Sss");
//                        Glide.with(getContext())
//                                .load(completeStorageRefranceToImage)
//                                .into(imageView);
//                        // Add all to your list
//                        mGalleryList.addAll(imageCards);
//                        Log.d("Firebase", "onSuccess: " + mGalleryList.toString());
//                        recyclerView.getAdapter().notifyDataSetChanged();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Toast.makeText(getActivity().getApplicationContext(), "Error Downloading Data", Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//
        mGalleryList.add(new ImageCard(R.drawable.one, ""));
        mGalleryList.add(new ImageCard(R.drawable.two, ""));
        mGalleryList.add(new ImageCard(R.drawable.three, ""));
        mGalleryList.add(new ImageCard(R.drawable.four, ""));
        mGalleryList.add(new ImageCard(R.drawable.five, ""));
        mGalleryList.add(new ImageCard(R.drawable.six, ""));
        mGalleryList.add(new ImageCard(R.drawable.seven, ""));
        mGalleryList.add(new ImageCard(R.drawable.eight, ""));
        mGalleryList.add(new ImageCard(R.drawable.nine, ""));
        mGalleryList.add(new ImageCard(R.drawable.ten, ""));

    }
}

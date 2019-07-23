package org.pcov.pcovannouncements.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import org.pcov.pcovannouncements.Adapters.NewsCardAdapter;
import org.pcov.pcovannouncements.AnnouncementsTextActivity;
import org.pcov.pcovannouncements.DataClass.NewsCard;
import org.pcov.pcovannouncements.R;
import org.pcov.pcovannouncements.Utils;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementFragment extends Fragment {

    private View v;
    private RecyclerView mRecyclerView;
    private ArrayList<NewsCard> mNewsList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNewsList = new ArrayList<>();
        getListItems();
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

        Log.d("Firebase", "During On Create View: " + mNewsList.toString());
        //Testing shows that the list is not fully updated yet with the Firestore data on On create.

        adapter.setOnClickListener(new NewsCardAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(int position) {
                //Navigate to the new activity, based off the card (Use position to distinguish cards)
                Intent i;
                i = new Intent(getActivity(), AnnouncementsTextActivity.class);
                i.putExtra("newsCardContext", mNewsList.get(position).getContext());
                i.putExtra("newsCardDate", mNewsList.get(position).getDate());
                startActivity(i);

            }
        });

        if (!Utils.isDeviceOnline(this.getActivity())) {
            Toast noInternetToast = Toast.makeText(getActivity().getApplicationContext(), getString(R.string.no_connection_announcements), Toast.LENGTH_LONG);
            noInternetToast.show();
        }

        return v;
    }

    private void getListItems() {
        db.collection("Announcements")
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
                            List<NewsCard> newsCards = documentSnapshots.toObjects(NewsCard.class);

                            // Add all to your list
                            mNewsList.addAll(newsCards);
                            Log.d("Firebase", "onSuccess: " + mNewsList.toString());
                            mRecyclerView.getAdapter().notifyDataSetChanged();
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
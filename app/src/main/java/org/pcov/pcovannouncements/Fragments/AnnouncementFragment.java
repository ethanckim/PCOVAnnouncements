package org.pcov.pcovannouncements.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.pcov.pcovannouncements.AboutUsWebviewActivity;
import org.pcov.pcovannouncements.Adapters.InfoCardAdapter;
import org.pcov.pcovannouncements.Adapters.NewsCardAdapter;
import org.pcov.pcovannouncements.DataClass.InformationCard;
import org.pcov.pcovannouncements.DataClass.NewsCard;
import org.pcov.pcovannouncements.MainActivity;
import org.pcov.pcovannouncements.R;

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

        mNewsList.add(new NewsCard(R.drawable.ic_arrow_forward, "PLACEHOLDER1", "PLACEHOLDER1", "nothing"));
        mNewsList.add(new NewsCard(R.drawable.ic_arrow_forward, "PLACEHOLDER2", "PLACEHOLDER2", "nothing"));
        mNewsList.add(new NewsCard(R.drawable.ic_arrow_forward, "PLACEHOLDER3", "PLACEHOLDER3", "nothing"));
        mNewsList.add(new NewsCard(R.drawable.ic_arrow_forward, "PLACEHOLDER4", "PLACEHOLDER4", "nothing"));
        mNewsList.add(new NewsCard(R.drawable.ic_arrow_forward, "PLACEHOLDER5", "PLACEHOLDER5", "nothing"));
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
                //Navigate to the new activity, based off the card (Use position the distinguish cards)
                Intent i;
                //TODO Change Activity to Navigate to.
                i = new Intent(getActivity(), AboutUsWebviewActivity.class);
                i.putExtra("position", position);
                startActivity(i);

            }
        });

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
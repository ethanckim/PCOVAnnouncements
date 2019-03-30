package org.pcov.pcovannouncements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class InformationFragment extends Fragment {

    private View v;
    private RecyclerView mRecyclerView;
    private ArrayList<InformationCard> minformationList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        minformationList = new ArrayList<>();
        minformationList.add(new InformationCard(R.drawable.ic_arrow_forward, "About Us"));
        minformationList.add(new InformationCard(R.drawable.ic_arrow_forward, "For Newcomers"));
        minformationList.add(new InformationCard(R.drawable.ic_arrow_forward, "Worship Services"));
        minformationList.add(new InformationCard(R.drawable.ic_arrow_forward, "Staff"));
        minformationList.add(new InformationCard(R.drawable.ic_arrow_forward, "Church History"));
        minformationList.add(new InformationCard(R.drawable.ic_arrow_forward, "Visit Us!"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_information, container, false);

        mRecyclerView = v.findViewById(R.id.infoRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(minformationList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new RecyclerViewAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(int position) {
                //Navigate to the new fragment

                InformationCardChild nextFrag = new InformationCardChild();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, nextFrag, nextFrag.getTag())
                        .commit();
            }
        });

        return v;
    }

}

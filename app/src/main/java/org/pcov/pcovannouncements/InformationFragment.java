package org.pcov.pcovannouncements;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class InformationFragment extends Fragment {

    private View v;
    private RecyclerView mRecyclerView;
    private ArrayList<InformationCard> informationList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        informationList  = new ArrayList<>();
        informationList.add(new InformationCard(R.drawable.ic_arrow_forward, "About Us"));
        informationList.add(new InformationCard(R.drawable.ic_arrow_forward, "For Newcomers"));
        informationList.add(new InformationCard(R.drawable.ic_arrow_forward, "Worship Services"));
        informationList.add(new InformationCard(R.drawable.ic_arrow_forward, "Staff"));
        informationList.add(new InformationCard(R.drawable.ic_arrow_forward, "Church History"));
        informationList.add(new InformationCard(R.drawable.ic_arrow_forward, "Visit Us!"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_information, container, false);

        mRecyclerView = v.findViewById(R.id.infoRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(informationList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        return v;
    }

}

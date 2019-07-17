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
import org.pcov.pcovannouncements.Adapters.InfoCardAdapter;
import org.pcov.pcovannouncements.Credits;
import org.pcov.pcovannouncements.DataClass.InformationCard;
import org.pcov.pcovannouncements.R;

import java.util.ArrayList;

public class InformationFragment extends Fragment {

    private View v;
    private RecyclerView mRecyclerView;
    private ArrayList<InformationCard> mInformationList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInformationList = new ArrayList<>();
        mInformationList.add(new InformationCard(R.drawable.ic_arrow_forward, getString(R.string.about_us)));
        mInformationList.add(new InformationCard(R.drawable.ic_arrow_forward, getString(R.string.for_newcomers)));
        mInformationList.add(new InformationCard(R.drawable.ic_arrow_forward, getString(R.string.worship_services)));
        mInformationList.add(new InformationCard(R.drawable.ic_arrow_forward, getString(R.string.church_history)));
        mInformationList.add(new InformationCard(R.drawable.ic_arrow_forward, getString(R.string.visit_us)));
        mInformationList.add(new InformationCard(R.drawable.ic_arrow_forward, getString(R.string.who_made_this_app)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_information, container, false);

        mRecyclerView = v.findViewById(R.id.infoRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        InfoCardAdapter adapter = new InfoCardAdapter(mInformationList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new InfoCardAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(int position) {
                //Navigate to the new activity, based off the card (Use position the distinguish cards)
                Intent i;
                if (position == 5) {
                    i = new Intent(getActivity(), Credits.class);
                    startActivity(i);
                } else {
                    i = new Intent(getActivity(), AboutUsWebviewActivity.class);
                    i.putExtra("position", position);
                    startActivity(i);
                }

            }
        });

        return v;
    }

}

package com.software.tongji.easygo.JournalDisplayMvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Journal;

import java.util.ArrayList;
import java.util.List;

public class JournalDisplayFragment extends Fragment {
    private RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.journal_display_fragment,container,false);
        mRecyclerView = view.findViewById(R.id.journal_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Journal> journals = new ArrayList<>();
        for(int i = 0;i<100;i++){
        Journal journal = new Journal();
        journals.add(journal);}
        mRecyclerView.setAdapter(new JournalAdapter(getContext(),journals));
        return view;
    }
}

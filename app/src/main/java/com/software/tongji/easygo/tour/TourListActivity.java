package com.software.tongji.easygo.tour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.TourLab;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TourListActivity extends AppCompatActivity {

    @BindView(R.id.tour_recycler_view)
    RecyclerView mTourRecyclerView;

    private TourListAdapter mTourListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_list);
        ButterKnife.bind(this);

        mTourRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTourListAdapter = new TourListAdapter(TourLab.get(this).getTourList());
        mTourRecyclerView.setAdapter(mTourListAdapter);
    }
}

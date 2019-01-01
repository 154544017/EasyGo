package com.software.tongji.easygo.tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.TourLab;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TourListActivity extends AppCompatActivity {

    public static final String EXTRA_TOUR_ID = "com.software.tongji.easygo.tour.extra.id";
    @BindView(R.id.tour_recycler_view)
    RecyclerView mTourRecyclerView;
    @BindView(R.id.tour_add)
    FloatingActionButton mTourAddButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_list);
        ButterKnife.bind(this);

        mTourRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        TourListAdapter tourListAdapter = new TourListAdapter(TourLab.get(this).getTourList(), tourId -> {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TOUR_ID, tourId);
            setResult(RESULT_OK, intent);
            finish();

        });
        mTourRecyclerView.setAdapter(tourListAdapter);

        mTourAddButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TOUR_ID, "00000000");
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}

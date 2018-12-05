package com.software.tongji.easygo.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Schedule;
import com.software.tongji.easygo.bean.ScheduleLab;
import com.software.tongji.easygo.newschedule.NewScheduleActivity;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleListFragment extends Fragment implements ScheduleListView{

    public static final int REQUEST_CODE_NEW_SCHEDULE = 0;

    @BindView(R.id.schedule_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.schedule_add)
    FloatingActionButton mAddSchedule;
    @BindView(R.id.schedule_toolbar)
    Toolbar mScheduleToolBar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.drawer_navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.no_schedule_alert)
    TextView mNoScheduleAlert;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private ScheduleListPresenter mScheduleListPresenter = new ScheduleListPresenter();
    private ScheduleAdapter mScheduleAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        ButterKnife.bind(this, view);
        mScheduleListPresenter.bind(this);
        mScheduleListPresenter.checkScheduleList();

        ((AppCompatActivity)getActivity()).setSupportActionBar(mScheduleToolBar);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, mScheduleToolBar,
                R.string.drawer_open, R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        View headerView = mNavigationView.getHeaderView(0);
        ImageView profileImage = (ImageView)headerView.findViewById(R.id.profile_image);
        if(profileImage != null){
            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawerLayout.closeDrawers();
                    mNavigationView.getMenu().getItem(1).setChecked(true);
                }
            });
        }

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mScheduleAdapter = new ScheduleAdapter(ScheduleLab.get(getContext()).getScheduleList(), mScheduleListPresenter);
        mRecyclerView.setAdapter(mScheduleAdapter);
        mRecyclerView.addItemDecoration(new SimplePaddingDecoration(getContext()));

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(mScheduleAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);

        mAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewScheduleActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NEW_SCHEDULE);
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CODE_NEW_SCHEDULE){
            String address = data.getStringExtra(NewScheduleActivity.NEW_SCHEDULE_ADDRESS);
            String date = data.getStringExtra(NewScheduleActivity.NEW_SCHEDULE_DATE);
            String time = data.getStringExtra(NewScheduleActivity.NEW_SCHEDULE_TIME);
            String type = data.getStringExtra(NewScheduleActivity.NEW_SCHEDULE_TYPE);
            String cost = data.getStringExtra(NewScheduleActivity.NEW_SCHEDULE_COST);
            String remark = data.getStringExtra(NewScheduleActivity.NEW_SCHEDULE_REMARK);
            Schedule schedule = new Schedule(address, date, time, type, cost, remark);
            mScheduleListPresenter.addSchedule(schedule);
            mScheduleListPresenter.checkScheduleList();
            mScheduleAdapter.notifyItemInserted(mScheduleListPresenter.getNewSchedulePosition());
        }
    }

    @Override
    public Context getScheduleListContext() {
        return getContext();
    }

    @Override
    public void showNoScheduleAlert() {
        mNoScheduleAlert.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoScheduleAlert() {
        mNoScheduleAlert.setVisibility(View.GONE);
    }
}

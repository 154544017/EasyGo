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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.about.AboutActivity;
import com.software.tongji.easygo.bean.Schedule;
import com.software.tongji.easygo.bean.Tour;
import com.software.tongji.easygo.bean.TourLab;
import com.software.tongji.easygo.checklist.CheckListActivity;
import com.software.tongji.easygo.newschedule.NewScheduleActivity;
import com.software.tongji.easygo.tour.SaveTourActivity;
import com.software.tongji.easygo.tour.TourListActivity;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleListFragment extends Fragment implements ScheduleListView {

    public static final int REQUEST_CODE_NEW_SCHEDULE = 0;
    public static final int REQUEST_CODE_SAVE_SHCEDULE = 1;
    private static final String DEFAULT_TOUR_ID = "00000000";

    @BindView(R.id.schedule_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.schedule_add)
    FloatingActionButton mAddSchedule;
    @BindView(R.id.schedule_save)
    FloatingActionButton mSaveSchedule;
    @BindView(R.id.line_planning)
    FloatingActionButton mLinePlanning;
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
    private MaterialDialog mDialog;
    private String mTourId;


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
                    mNavigationView.getMenu().getItem(0).setChecked(true);
                }
            });
        }

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        //mDrawerLayout.closeDrawers();
                        switch (item.getItemId()) {
                            case R.id.navigation_item_about:
                                Intent aboutIntent = new Intent(getActivity(), AboutActivity.class);
                                startActivity(aboutIntent);
                                break;
                            case R.id.navigation_tour_list:
                                Intent tourListIntent = new Intent(getActivity(), TourListActivity.class);
                                startActivity(tourListIntent);
                                break;
                            case R.id.navigation_item_check_list:
                                Intent checkIntent = new Intent(getActivity(), CheckListActivity.class);
                                startActivity(checkIntent);
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                }
        );

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        mScheduleAdapter = new ScheduleAdapter(mScheduleListPresenter);
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

        mSaveSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SaveTourActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SAVE_SHCEDULE);
            }
        });

        mLinePlanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
            double latitude = data.getDoubleExtra(NewScheduleActivity.NEW_SCHEDULE_LAT,0);
            double longitude = data.getDoubleExtra(NewScheduleActivity.NEW_SCHEDULE_LON,0);
            String date = data.getStringExtra(NewScheduleActivity.NEW_SCHEDULE_DATE);
            String time = data.getStringExtra(NewScheduleActivity.NEW_SCHEDULE_TIME);
            String type = data.getStringExtra(NewScheduleActivity.NEW_SCHEDULE_TYPE);
            String cost = data.getStringExtra(NewScheduleActivity.NEW_SCHEDULE_COST);
            String remark = data.getStringExtra(NewScheduleActivity.NEW_SCHEDULE_REMARK);
            int position = mScheduleListPresenter.getNewSchedulePosition();
            Schedule schedule = new Schedule(mTourId,address,latitude,longitude,date,time,type,cost,remark,position);
            mScheduleListPresenter.addSchedule(schedule);

        }else if(requestCode == REQUEST_CODE_SAVE_SHCEDULE){
            String title = data.getStringExtra(SaveTourActivity.NEW_TOUR_TITLE);
            String remark = data.getStringExtra(SaveTourActivity.NEW_TOUR_REMARK);
            Tour tour = new Tour(title,remark);
            mScheduleListPresenter.updateSchedules(mTourId, tour.getId());
            TourLab.get(getContext()).addNewTour(tour);
            mTourId = DEFAULT_TOUR_ID;

        }
    }

    public void getSchedules(){
        if (mTourId == null) {
            TourLab tourLab = TourLab.get(getContext());
            if (tourLab.size() == 0) {
                mTourId = DEFAULT_TOUR_ID;
            } else {
                mTourId = tourLab.latestTourId();
            }
        }
        mScheduleListPresenter.getSchedules(mTourId);
    }

    public void setTourId(String tourId){
        mTourId = tourId;
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

    @Override
    public void showDialog() {
        if (mDialog == null || mDialog.isCancelled()) {
            mDialog = new MaterialDialog.Builder(getContext())
                    .title(R.string.app_name)
                    .content("Please Wait...")
                    .progress(true, 0)
                    .show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getSchedules();
    }


    @Override
    public void dismissDialog() {
        mDialog.dismiss();
    }

    @Override
    public void refreshView(List<Schedule> scheduleList) {
        mScheduleAdapter.updateList(scheduleList);
        mScheduleAdapter.notifyDataSetChanged();
    }
}

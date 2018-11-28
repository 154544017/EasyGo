package com.software.tongji.easygo.schedule;

import android.app.Activity;
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

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Schedule;
import com.software.tongji.easygo.bean.ScheduleLab;
import com.software.tongji.easygo.newschedule.NewScheduleActivity;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleListFragment extends Fragment {

    public static final int REQUEST_CODE_NEW_SHCEDULE = 0;

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

    private ActionBarDrawerToggle mActionBarDrawerToggle;

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
        mScheduleAdapter = new ScheduleAdapter(ScheduleLab.get(getContext()).getScheduleList());
        mRecyclerView.setAdapter(mScheduleAdapter);
        mRecyclerView.addItemDecoration(new SimplePaddingDecoration(getContext()));

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(mScheduleAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);

        mAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewScheduleActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NEW_SHCEDULE);
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CODE_NEW_SHCEDULE){
            String type = data.getStringExtra(NewScheduleActivity.NEW_TYPE);
            String address = data.getStringExtra(NewScheduleActivity.NEW_ADDRESS);
            String beginTime = data.getStringExtra(NewScheduleActivity.NEW_BEGIN_TIME);
            Schedule schedule = new Schedule(Schedule.ScheduleType.DINING, address, beginTime);
            if(type.equals("HOTEL")){
                schedule.setScheduleType(Schedule.ScheduleType.HOTEL);
            }else if(type.equals("TRANSPORT")){
                schedule.setScheduleType(Schedule.ScheduleType.TRANSPORT);
            }else if(type.equals("ATTRACTIONS")){
                schedule.setScheduleType(Schedule.ScheduleType.ATTRACTIONS);
            }
            ScheduleLab.get(getContext()).getScheduleList().add(0, schedule);
            mScheduleAdapter.notifyItemInserted(0);
        }
    }
}

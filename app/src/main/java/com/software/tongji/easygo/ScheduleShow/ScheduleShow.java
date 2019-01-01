package com.software.tongji.easygo.ScheduleShow;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Schedule;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleShow extends AppCompatActivity {

    private static final String EXTRA_SCHEDULE = "com.software.tongji.easygo.extra_schedule";

    @BindView(R.id.show_schedule_address)
    TextInputEditText mScheduleAddress;
    @BindView(R.id.show_schedule_date)
    TextInputEditText mScheduleDate;
    @BindView(R.id.show_schedule_time)
    TextInputEditText mScheduleTime;
    @BindView(R.id.show_schedule_cost)
    TextInputEditText mScheduleCost;
    @BindView(R.id.show_schedule_remark)
    TextInputEditText mScheduleRemark;

    public static Intent newIntent(Context packageContext, Schedule schedule){
        Intent intent = new Intent(packageContext, ScheduleShow.class);
        intent.putExtra(EXTRA_SCHEDULE, schedule);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_show);
        ButterKnife.bind(this);

        Schedule schedule = (Schedule) getIntent().getSerializableExtra(EXTRA_SCHEDULE);
        if (schedule.getAddress() != null) {
            mScheduleAddress.setText(schedule.getAddress());
        }
        if (schedule.getDate() != null) {
            mScheduleDate.setText(schedule.getDate());
        }
        if (schedule.getTime() != null) {
            mScheduleTime.setText(schedule.getTime());
        }
        if (schedule.getCost() != null) {
            mScheduleCost.setText(schedule.getCost());
        }
        if (schedule.getRemark() != null) {
            mScheduleRemark.setText(schedule.getRemark());
        }
    }
}

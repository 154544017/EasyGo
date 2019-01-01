package com.software.tongji.easygo.schedule;

import android.content.Context;

import com.software.tongji.easygo.bean.Schedule;

import java.util.List;

public interface ScheduleListView {
    Context getScheduleListContext();
    void showNoScheduleAlert();
    void hideNoScheduleAlert();
    void showDialog();
    void dismissDialog();
    void refreshView(List<Schedule> scheduleList);
}

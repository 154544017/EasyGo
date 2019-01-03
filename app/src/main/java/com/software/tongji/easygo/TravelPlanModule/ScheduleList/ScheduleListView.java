package com.software.tongji.easygo.TravelPlanModule.ScheduleList;

import android.content.Context;

import com.software.tongji.easygo.Bean.Schedule;

import java.util.List;

public interface ScheduleListView {
    Context getScheduleListContext();
    void showNoScheduleAlert();
    void hideNoScheduleAlert();
    void showDialog();
    void dismissDialog();
    void refreshView(List<Schedule> scheduleList);
}

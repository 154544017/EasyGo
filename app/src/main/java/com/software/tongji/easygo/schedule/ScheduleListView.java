package com.software.tongji.easygo.schedule;

import android.content.Context;

public interface ScheduleListView {
    Context getScheduleListContext();
    void showNoScheduleAlert();
    void hideNoScheduleAlert();
}

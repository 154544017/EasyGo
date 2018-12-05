package com.software.tongji.easygo.schedule;

import com.software.tongji.easygo.bean.Schedule;
import com.software.tongji.easygo.bean.ScheduleLab;

import java.util.List;

public class ScheduleListPresenter {
    private ScheduleListView mScheduleListView;
    private List<Schedule> mScheduleList;

    public void bind(ScheduleListView scheduleListView){
        mScheduleListView = scheduleListView;
        mScheduleList = ScheduleLab.get(mScheduleListView.getScheduleListContext()).getScheduleList();
    }

    public void checkScheduleList(){
        if(mScheduleList.size() < 1){
            mScheduleListView.showNoScheduleAlert();
        }else{
            mScheduleListView.hideNoScheduleAlert();
        }
    }

    public void addSchedule(Schedule schedule){
        mScheduleList.add(schedule);
    }

    public int getNewSchedulePosition(){
        return mScheduleList.size() - 1;
    }
}

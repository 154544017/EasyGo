package com.software.tongji.easygo.bean;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ScheduleLab {

    private static ScheduleLab sScheduleLab;

    private Context mContext;
    private List<Schedule> mScheduleList;

    public static ScheduleLab get(Context context){
        if(sScheduleLab == null){
            sScheduleLab = new ScheduleLab(context);
        }
        return sScheduleLab;
    }

    private ScheduleLab(Context context){
        mContext = context;
        mScheduleList = new ArrayList<>();
    }

    public List<Schedule> getScheduleList() {
        return mScheduleList;
    }

    public void addSchedule(Schedule schedule){
        mScheduleList.add(schedule);
    }
}

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
        mContext = context.getApplicationContext();
        mScheduleList = new ArrayList<>();

        mScheduleList.add(new Schedule(Schedule.ScheduleType.DINING, "DiningHall", "7:00am"));
        mScheduleList.add(new Schedule(Schedule.ScheduleType.ATTRACTIONS, "Mountain", "8:30am"));
        mScheduleList.add(new Schedule(Schedule.ScheduleType.ATTRACTIONS, "River", "1:30pm"));
        mScheduleList.add(new Schedule(Schedule.ScheduleType.TRANSPORT, "Car", "6:00pm"));
        mScheduleList.add(new Schedule(Schedule.ScheduleType.HOTEL, "HOTEL", "12:00pm"));
    }

    public List<Schedule> getScheduleList() {
        return mScheduleList;
    }

    public void addSchedule(Schedule schedule){
        mScheduleList.add(schedule);
    }
}

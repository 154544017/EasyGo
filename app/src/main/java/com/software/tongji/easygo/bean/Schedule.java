package com.software.tongji.easygo.bean;

import android.util.ArrayMap;

import com.software.tongji.easygo.R;

import java.util.Map;

public class Schedule {

    public ScheduleType getScheduleType() {
        return mScheduleType;
    }


    private ScheduleType mScheduleType;
    private String mAddress;
    private String mBeginTime;

    public Schedule(ScheduleType scheduleType, String address, String beginTime){
        mScheduleType = scheduleType;
        mAddress = address;
        mBeginTime = beginTime;
    }

    public void setScheduleType(ScheduleType scheduleType) {
        mScheduleType = scheduleType;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getBeginTime() {
        return mBeginTime;
    }

    public void setBeginTime(String beginTime) {
        mBeginTime = beginTime;
    }

    public enum ScheduleType{
        DINING, HOTEL, TRANSPORT, ATTRACTIONS
    }

}

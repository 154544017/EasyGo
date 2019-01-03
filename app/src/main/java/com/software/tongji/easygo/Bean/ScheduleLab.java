package com.software.tongji.easygo.Bean;

import android.content.Context;

import org.litepal.LitePal;

import java.util.Collections;
import java.util.List;

public class ScheduleLab {

    private static ScheduleLab sScheduleLab;
    private List<Schedule> mSchedules;
    private String mCurrentTourId;
    private int mPosition;//新加入的schedule的位置

    public static ScheduleLab get(Context context){
        if(sScheduleLab == null){
            sScheduleLab = new ScheduleLab(context);
        }
        return sScheduleLab;
    }

    private ScheduleLab(Context context){
        Context context1 = context;
    }

    public List<Schedule> getScheduleList(String tourId) {
        if(mSchedules == null || !tourId.equals(mCurrentTourId)){
            mSchedules = LitePal.where("mTourId = ?", tourId)
                .find(Schedule.class);
        }
        mPosition = mSchedules.size();
        mCurrentTourId = tourId;
        return mSchedules;
    }

    public void addSchedule(Schedule schedule){
        mSchedules.add(schedule);
        mPosition++;
        schedule.save();
    }

    public void deleteSchedule(int position){
        String tourId = mSchedules.get(0).getTourId();
        LitePal.deleteAll(Schedule.class,"mTourId = ?", tourId);
        LitePal.markAsDeleted(mSchedules);
        for(Schedule schedule:mSchedules){
            if(schedule.getPosition() > position){
                schedule.setPosition(schedule.getPosition()-1);
            }
        }
        mSchedules.remove(position);
        mPosition--;
        LitePal.saveAll(mSchedules);
    }
    public void changePosition(int p1, int p2){
        String tourId = mSchedules.get(0).getTourId();
        LitePal.deleteAll(Schedule.class,"mTourId = ?", tourId);
        LitePal.markAsDeleted(mSchedules);
        mSchedules.get(p1).setPosition(p2);
        mSchedules.get(p2).setPosition(p1);
        Collections.swap(mSchedules, p1, p2);
        LitePal.saveAll(mSchedules);
    }

    public void updateTour(String oldTourId, String newTourId){
        Schedule schedule = new Schedule();
        schedule.setTourId(newTourId);
        schedule.updateAll("mTourId = ?", oldTourId);
    }

    public int getPosition(){
        return mPosition;
    }
}

package com.software.tongji.easygo.schedule;

import com.software.tongji.easygo.bean.Schedule;
import com.software.tongji.easygo.bean.ScheduleLab;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ScheduleListPresenter {
    private ScheduleListView mScheduleListView;
    private ScheduleLab mScheduleLab;

    public void bind(ScheduleListView scheduleListView){
        mScheduleListView = scheduleListView;
        mScheduleLab = ScheduleLab.get(mScheduleListView.getScheduleListContext());
    }

    public void getSchedules(String tourId){
        mScheduleListView.showDialog();
        rx.Observable.create((Observable.OnSubscribe<List<Schedule>>) subscriber -> {
            List<Schedule> schedules = mScheduleLab.getScheduleList(tourId);
            subscriber.onNext(schedules);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Schedule>>() {
                    @Override
                    public void onCompleted() {
                        mScheduleListView.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mScheduleListView.dismissDialog();
                    }

                    @Override
                    public void onNext(List<Schedule> schedules) {
                        if (schedules.size() > 0) {
                            mScheduleListView.hideNoScheduleAlert();
                            mScheduleListView.refreshView(schedules);
                        } else {
                            mScheduleListView.showNoScheduleAlert();
                            List<Schedule> schedulesList = new ArrayList<>();
                            mScheduleListView.refreshView(schedulesList);
                        }
                    }
                });

    }


    public void updateSchedules(String oldTourId,String newTourId){
        mScheduleLab.updateTour(oldTourId,newTourId);
    }
    public void addSchedule(Schedule schedule){
      mScheduleLab.addSchedule(schedule);
      getSchedules(schedule.getTourId());
    }

    public void deleteSchedule(Schedule schedule){
        mScheduleLab.deleteSchedule(schedule.getPosition());
        getSchedules(schedule.getTourId());
    }

    public void swapSchedule(Schedule schedule1, Schedule schedule2){
        mScheduleLab.changePosition(schedule1.getPosition(),schedule2.getPosition());
        getSchedules(schedule1.getTourId());
    }

    public int getNewSchedulePosition(){
        return  mScheduleLab.getPosition();
    }
}

package com.software.tongji.easygo.bean;

import android.content.Context;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class TourLab {
    public static TourLab sTourLab;

    private Context mContext;
    private List<Tour> mTourList;

    public static TourLab get(Context context){
        if(sTourLab == null){
            sTourLab = new TourLab(context);
        }
        return sTourLab;
    }

    private TourLab(Context context){
        mContext = context;
        mTourList = new ArrayList<>();
    }

    public List<Tour> getTourList() {
        return LitePal.findAll(Tour.class);
    }

    public void addNewTour(Tour tour){
        tour.save();
    }

    public int size(){
        return LitePal.count(Tour.class);
    }

    public String latestTourId(){
        List<Tour> tours = LitePal.findAll(Tour.class);
        return tours.get(tours.size()-1).getId();
    }
}

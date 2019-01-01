package com.software.tongji.easygo.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Schedule extends LitePalSupport implements Serializable {

    private String mTourId;
    private String mAddress;
    private double mLatPoint;
    private double mLonPoint;
    private String mDate;
    private String mTime;
    private String mType;
    private String mCost;
    private String mRemark;
    private int mPosition;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public double getLatPoint() {
        return mLatPoint;
    }

    public void setLatPoint(double latPoint) {
        mLatPoint = latPoint;
    }

    public double getLonPoint() {
        return mLonPoint;
    }

    public void setLonPoint(double lonPoint) {
        mLonPoint = lonPoint;
    }

    public Schedule(){}

    public Schedule(String tourId, String address, double latPoint, double lonPoint, String date, String time, String type, String cost, String remark, int position) {
        mTourId = tourId;
        mAddress = address;
        mLatPoint = latPoint;
        mLonPoint = lonPoint;
        mDate = date;
        mTime = time;
        mType = type;
        mCost = cost;
        mRemark = remark;
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public String getTourId() {
        return mTourId;
    }

    public void setTourId(String tourId) {
        mTourId = tourId;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getCost() {
        return mCost;
    }

    public void setCost(String cost) {
        mCost = cost;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String remark) {
        mRemark = remark;
    }
}

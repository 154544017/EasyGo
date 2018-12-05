package com.software.tongji.easygo.bean;

import android.util.ArrayMap;

import com.software.tongji.easygo.R;

import java.util.Map;

public class Schedule {

    private String mAddress;
    private String mDate;
    private String mTime;
    private String mType;
    private String mCost;
    private String mRemark;

    public Schedule(String address, String date, String time,
                    String type, String cost, String remark){

        mAddress = address;
        mDate = date;
        mTime = time;
        mType = type;
        mCost = cost;
        mRemark = remark;
    }


    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
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

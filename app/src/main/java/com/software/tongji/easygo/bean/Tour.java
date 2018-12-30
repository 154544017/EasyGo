package com.software.tongji.easygo.bean;

public class Tour {
    private String mTitle;
    private String mRemark;

    public Tour(String title, String remark){
        mTitle = title;
        mRemark = remark;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String remark) {
        mRemark = remark;
    }
}

package com.software.tongji.easygo.bean;

import org.litepal.crud.LitePalSupport;

import java.util.UUID;

public class Tour extends LitePalSupport {
    private String mTitle;
    private String mRemark;
    private String mId;

    public String getId() {
        return mId;
    }

    public Tour(String title, String remark){
        mId = UUID.randomUUID().toString();
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

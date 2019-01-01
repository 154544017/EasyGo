package com.software.tongji.easygo.bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.UUID;

public class Journal extends LitePalSupport implements Serializable {
    @SerializedName("id")
    private String mId;
    @SerializedName("cover")
    private String mCoverUrl;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("date")
    private String mDate;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("friend")
    private String mFriends;
    @SerializedName("content")
    private String mContent;
    @SerializedName("province")
    private String mProvince;

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String province) {
        mProvince = province;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        mCoverUrl = coverUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String uuid){
        mId = uuid;
    }
    public Journal(){
        mId = UUID.randomUUID().toString();
    }
    public Journal(String title, String date, String location, String friends, String content){
        mId = UUID.randomUUID().toString();
        mTitle = title;
        mDate = date;
        mLocation = location;
        mFriends = friends;
        mContent = content;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getFriends() {
        return mFriends;
    }

    public void setFriends(String friends) {
        mFriends = friends;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getCoverFileName(){
        return "IMG_" + getId() + ".jpg";
    }
}

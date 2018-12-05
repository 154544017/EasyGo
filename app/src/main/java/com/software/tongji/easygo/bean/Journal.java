package com.software.tongji.easygo.bean;

public class Journal {
    private String mTitle;
    private String mDate;
    private String mLocation;
    private String mFriends;
    private String mContent;

    public Journal(){

    }

    public Journal(String title, String date, String location, String friends, String content){
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
}

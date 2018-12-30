package com.software.tongji.easygo.bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

public class Attraction extends LitePalSupport implements Serializable {
    @SerializedName("name")
    private String mName;
    @SerializedName("province")
    private String mProvince;
    @SerializedName("info")
    private String mIntroduction;
    @SerializedName("time")
    private String mBestTime;
    @SerializedName("city")
    private String mCity;

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    @SerializedName("mimages")
    private List<String> mImages;



    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String province) {
        mProvince = province;
    }

    public String getIntroduction() {
        return mIntroduction;
    }

    public void setIntroduction(String introduction) {
        mIntroduction = introduction;
    }

    public String getBestTime() {
        return mBestTime;
    }

    public void setBestTime(String bestTime) {
        mBestTime = bestTime;
    }

    public List<String> getImages() {
        return mImages;
    }

    public void setImages(List<String> images) {
        mImages = images;
    }
}

package com.software.tongji.easygo.Bean;

import java.util.List;

public class MyMap {

    private List<Province> mProvinceList;
    private float Max_x;
    private float Min_x;
    private float Max_y;
    private float Min_y;

    public float getMin_x() {
        return Min_x;
    }

    public void setMin_x(float min_x) {
        Min_x = min_x;
    }

    public float getMax_y() {
        return Max_y;
    }

    public void setMax_y(float max_y) {
        Max_y = max_y;
    }

    public float getMin_y() {
        return Min_y;
    }

    public void setMin_y(float min_y) {
        Min_y = min_y;
    }

    public float getMax_x() {
        return Max_x;
    }

    public List<Province> getProvinceList() {
        return mProvinceList;
    }

    public void setProvinceList(List<Province> provinceList) {
        this.mProvinceList = provinceList;
    }

    public void setMax_x(float max_x) {
        Max_x = max_x;
    }

}
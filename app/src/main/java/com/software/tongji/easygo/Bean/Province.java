package com.software.tongji.easygo.Bean;

import android.graphics.Path;

import org.litepal.crud.LitePalSupport;

import java.util.List;

public class Province extends LitePalSupport {
    private boolean mIsLocked = true;
    private List<String> mPathStringList;
    private String mPinYin;
    private String mName;
    private List<Path> mPathList;
    private int mLineColor;
    private List<Lasso> mPathLasso;
    private int mColor;

    public String getPinYin() {
        return mPinYin;
    }

    public void setPinYin(String pinYin) {
        mPinYin = pinYin;
    }

    public boolean isLocked() {
        return mIsLocked;
    }

    public void setLocked(boolean locked) {
        mIsLocked = locked;
    }

    public List<Path> getPathList() {
        return mPathList;
    }

    public int getLineColor() {
        return mLineColor;
    }

    public void setLineColor(int lineColor) {
        mLineColor = lineColor;
    }

    public List<Lasso> getPathLasso() {
        return mPathLasso;
    }

    public void setPathLasso(List<Lasso> pathLasso) {
        mPathLasso = pathLasso;
    }

    public void setPathList(List<Path> pathList) {
        this.mPathList = pathList;
    }

    public int getColor() {
        return mColor;
    }
    public void setColor(int color) {
        this.mColor = color;
    }
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        this.mName = name;
    }

    public List<String> getPathStringList() {
        return mPathStringList;
    }

    public void setPathStringList(List<String> pathStringList) {
        mPathStringList = pathStringList;
    }
}

package com.software.tongji.easygo.bean;

public class CheckItem {
    private String mName;
    private boolean mCheck;

    public CheckItem(String name, boolean checked){
        mName = name;
        mCheck = checked;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isCheck() {
        return mCheck;
    }

    public void setCheck(boolean check) {
        mCheck = check;
    }
}

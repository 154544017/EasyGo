package com.software.tongji.easygo.bean;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class CheckItemLab {
    public static CheckItemLab sCheckItemLab;

    private Context mContext;
    private List<CheckItem> mCheckItemList;

    public static CheckItemLab get(Context context){
        if(sCheckItemLab == null){
            sCheckItemLab = new CheckItemLab(context);
        }
        return sCheckItemLab;
    }

    private CheckItemLab(Context context){
        mContext = context;
        mCheckItemList = new ArrayList<>();
    }

    public List<CheckItem> getCheckItemList() {
        return mCheckItemList;
    }

    public void addCheckItem(CheckItem checkItem){
        mCheckItemList.add(checkItem);
    }
}

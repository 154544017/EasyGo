package com.software.tongji.easygo.bean;

import android.content.Context;

import org.litepal.LitePal;

import java.util.List;

public class CheckItemLab {
    public static CheckItemLab sCheckItemLab;
    private Context mContext;


    public static CheckItemLab get(Context context){
        if(sCheckItemLab == null){
            sCheckItemLab = new CheckItemLab(context);
        }
        return sCheckItemLab;
    }

    private CheckItemLab(Context context){
        mContext = context.getApplicationContext();
    }

    public List<CheckItem> getCheckItemList() {
        List<CheckItem> items = LitePal.findAll(CheckItem.class);
        return items;
    }

    public void addCheckItem(String checkItemName){
        CheckItem item = new CheckItem(checkItemName,false);
        item.save();
    }

    public void deleteCheckItem(String checkItemName){
        LitePal.deleteAll(CheckItem.class,"mName = ?", checkItemName);
    }

    public void changeItemState(String name, Boolean state){
        CheckItem checkItem = new CheckItem(name, state);
        checkItem.updateAll("mName = ?", name);
    }
}

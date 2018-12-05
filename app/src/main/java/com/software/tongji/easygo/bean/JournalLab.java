package com.software.tongji.easygo.bean;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class JournalLab {

    public static JournalLab sJournalLab;

    private Context mContext;
    private List<Journal> mJournalList;

    public static JournalLab get(Context context){
        if(sJournalLab == null){
            sJournalLab = new JournalLab(context);
        }
        return sJournalLab;
    }

    private JournalLab(Context context){
        mContext = context;
        mJournalList = new ArrayList<>();
        Journal journal1 = new Journal("beijing","2018/3/3","tokyo","@louoyu","very sad");
        mJournalList.add(journal1);
    }

    public List<Journal> getJournalList() {
        return mJournalList;
    }

    public void addJournal(Journal journal){
        mJournalList.add(journal);
    }
}

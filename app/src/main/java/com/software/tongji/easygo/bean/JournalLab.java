package com.software.tongji.easygo.bean;

import android.content.Context;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JournalLab {

    public static JournalLab sJournalLab;

    private Context mContext;

    public static JournalLab get(Context context){
        if(sJournalLab == null){
            sJournalLab = new JournalLab(context);
        }
        return sJournalLab;
    }

    private JournalLab(Context context){
        mContext = context.getApplicationContext();
    }

    public int size(){
        int count  = LitePal.count(Journal.class);
        return count;
    }

    public void addJournal(Journal journal){
        journal.save();
    }

    public void updateJournal(Journal journal){
        journal.updateAll("mId = ?", journal.getId());
    }

    public void deleteJournal(Journal journal){
        LitePal.deleteAll(Journal.class, "mId = ?", journal.getId());
    }

    public Journal getJournal(String uuid){
        List<Journal> journals = LitePal.where("mId = ?", uuid)
                .find(Journal.class);
        return journals.get(0);
    }

    public List<Journal> getJournalList() {
        List<Journal> journals = LitePal.findAll(Journal.class);
        return journals;
    }

    public File getCoverFIle(Journal journal){
        File fileDir = mContext.getFilesDir();
        return new File(fileDir, journal.getCoverFileName());
    }

}

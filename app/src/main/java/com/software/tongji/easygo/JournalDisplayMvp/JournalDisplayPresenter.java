package com.software.tongji.easygo.JournalDisplayMvp;

import com.software.tongji.easygo.bean.Journal;
import com.software.tongji.easygo.bean.JournalLab;

import java.util.List;

public class JournalDisplayPresenter {
    private JournalDisplayView mJournalDisplayView;
    private List<Journal> mJournalList;

    public void bind(JournalDisplayView journalDisplayView){
        mJournalDisplayView = journalDisplayView;
        mJournalList = JournalLab.get(mJournalDisplayView.getJournalListContetx()).getJournalList();
    }

    public void addJournal(Journal journal){
        mJournalList.add(0, journal);
    }

    public int getNewJournalPosition(){
        return 0;
    }
}

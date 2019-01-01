package com.software.tongji.easygo.journaledit;

import android.content.Context;

import com.software.tongji.easygo.bean.Journal;
import com.software.tongji.easygo.bean.JournalLab;

public class JournalEditPresenter {
    private JournalEditView mJournalEditView;
    private Context mContext;

    public void bind(Context context, JournalEditView editView){
        mContext = context;
        mJournalEditView = editView;
    }

    public void saveJournal(Journal journal){
        mJournalEditView.showLoadingDialog();
        JournalLab.get(mContext).updateJournal(journal);
        mJournalEditView.dismissLoadingDialog();
    }

    public void addJournal(Journal journal){
        mJournalEditView.showLoadingDialog();
        JournalLab.get(mContext).addJournal(journal);
        mJournalEditView.dismissLoadingDialog();
    }
}

package com.software.tongji.easygo.JournalDisplayMvp;

import android.content.Context;

import com.software.tongji.easygo.bean.Journal;

import java.util.List;

public interface JournalDisplayView {
    Context getJournalListContext();
    void showLoadingDialog();
    void dismissLoadingDialog();
    void showError(String message);
    void updateAdapter(List<Journal> journalList);
}

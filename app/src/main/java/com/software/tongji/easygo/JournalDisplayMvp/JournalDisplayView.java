package com.software.tongji.easygo.JournalDisplayMvp;

import com.software.tongji.easygo.bean.Journal;

import java.util.List;

public interface JournalDisplayView {
    void showLoadingDialog();
    void dismissLoadingDialog();
    void showError(String message);
    void updateAdapter(List<Journal> journalList);
    void noJournals();
}

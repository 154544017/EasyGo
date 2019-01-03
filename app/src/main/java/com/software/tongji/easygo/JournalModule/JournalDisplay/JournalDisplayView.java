package com.software.tongji.easygo.JournalModule.JournalDisplay;

import com.software.tongji.easygo.Bean.Journal;

import java.util.List;

public interface JournalDisplayView {
    void showLoadingDialog();
    void dismissLoadingDialog();
    void showError(String message);
    void updateAdapter(List<Journal> journalList);
    void noJournals();
}

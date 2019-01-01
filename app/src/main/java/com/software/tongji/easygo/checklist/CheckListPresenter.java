package com.software.tongji.easygo.checklist;

public interface CheckListPresenter {
    void getCheckLists();
    void addCheckItem(String name);
    void deleteCheckItem(String name);
    void changeItemState(String name, Boolean state);
}

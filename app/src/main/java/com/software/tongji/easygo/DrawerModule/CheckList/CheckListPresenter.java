package com.software.tongji.easygo.DrawerModule.CheckList;

public interface CheckListPresenter {
    void getCheckLists();
    void addCheckItem(String name);
    void deleteCheckItem(String name);
    void changeItemState(String name, Boolean state);
}

package com.software.tongji.easygo.checklist;

import com.software.tongji.easygo.bean.CheckItem;

public interface CheckListPresenter {
    void getCheckLists();
    void addCheckItem(String name);
    void deleteCheckItem(String name);
    void changeItemState(String name, Boolean state);
}

package com.software.tongji.easygo.checklist;

import com.software.tongji.easygo.bean.CheckItem;

import java.util.List;

public interface CheckListView {
    void showLoadingDialog();
    void dismissLoadingDialog();
    void showError(String message);
    void refreshView(List<CheckItem> checkItemList);
}

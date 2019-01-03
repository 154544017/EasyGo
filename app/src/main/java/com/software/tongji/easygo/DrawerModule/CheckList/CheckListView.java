package com.software.tongji.easygo.DrawerModule.CheckList;

import com.software.tongji.easygo.Bean.CheckItem;

import java.util.List;

public interface CheckListView {
    void showLoadingDialog();
    void dismissLoadingDialog();
    void showError(String message);
    void refreshView(List<CheckItem> checkItemList);
}

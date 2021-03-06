package com.software.tongji.easygo.DrawerModule.CheckList;

import android.content.Context;

import com.software.tongji.easygo.Bean.CheckItem;
import com.software.tongji.easygo.Bean.CheckItemLab;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CheckListPresenterImpl implements CheckListPresenter {
    private CheckListView mCheckListView;
    private CheckItemLab mCheckItemLab;

    public CheckListPresenterImpl(Context context,CheckListView view){
        mCheckListView = view;
        Context context1 = context;
        mCheckItemLab = CheckItemLab.get(context1);
    }

    @Override
    public void getCheckLists() {
        mCheckListView.showLoadingDialog();
        rx.Observable.create((Observable.OnSubscribe<List<CheckItem>>) subscriber -> {
            List<CheckItem> items = mCheckItemLab.getCheckItemList();
            subscriber.onNext(items);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CheckItem>>() {
                    @Override
                    public void onCompleted() {
                        mCheckListView.dismissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<CheckItem> items) {
                        mCheckListView.refreshView(items);
                    }
                });
    }

    @Override
    public void addCheckItem(String checkItemName) {
        mCheckItemLab.addCheckItem(checkItemName);
        getCheckLists();
    }

    @Override
    public void deleteCheckItem(String checkItemName) {
        mCheckItemLab.deleteCheckItem(checkItemName);
        getCheckLists();
    }

    @Override
    public void changeItemState(String name, Boolean state) {
        mCheckItemLab.changeItemState(name,state);
    }
}

package com.software.tongji.easygo.checklist;

import android.content.Context;

import com.software.tongji.easygo.bean.CheckItem;
import com.software.tongji.easygo.bean.CheckItemLab;

import java.util.List;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CheckListPresenterImpl implements CheckListPresenter {
    private CheckListView mCheckListView;
    private Context mContext;
    private CheckItemLab mCheckItemLab;

    public CheckListPresenterImpl(Context context,CheckListView view){
        mCheckListView = view;
        mContext = context;
        mCheckItemLab = CheckItemLab.get(mContext);
    }

    @Override
    public void getCheckLists() {
        mCheckListView.showLoadingDialog();
        rx.Observable.create(new rx.Observable.OnSubscribe<List<CheckItem>>() {
            @Override
            public void call(Subscriber<? super List<CheckItem>> subscriber) {
                List<CheckItem> items = mCheckItemLab.getCheckItemList();
                subscriber.onNext(items);
                subscriber.onCompleted();
            }
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

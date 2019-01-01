package com.software.tongji.easygo.JournalDisplayMvp;

import android.content.Context;

import com.software.tongji.easygo.bean.Journal;
import com.software.tongji.easygo.bean.JournalLab;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JournalDisplayPresenter {
    private JournalDisplayView mJournalDisplayView;
    private JournalLab mJournalLab;

    public void deleteJournal(Journal journal){
        mJournalLab.deleteJournal(journal);
    }

    public void getJournals(){
        mJournalDisplayView.showLoadingDialog();
        rx.Observable.create((Observable.OnSubscribe<List<Journal>>) subscriber -> {
            List<Journal> journals = mJournalLab.getJournalList();
            subscriber.onNext(journals);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Journal>>() {
            @Override
            public void onCompleted() {
                mJournalDisplayView.dismissLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Journal> journals) {
                if (journals.size() > 0) {
                    mJournalDisplayView.updateAdapter(journals);
                } else {
                    mJournalDisplayView.noJournals();
                }
            }
        });
    }

    public void getJournalsByProvince(String provinceName){
        mJournalDisplayView.showLoadingDialog();
        rx.Observable.create((Observable.OnSubscribe<List<Journal>>) subscriber -> {
            List<Journal> journals = mJournalLab.getJournalListByProvince(provinceName);
            subscriber.onNext(journals);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Journal>>() {
                    @Override
                    public void onCompleted() {
                        mJournalDisplayView.dismissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Journal> journals) {
                        if (journals.size() > 0) {
                            mJournalDisplayView.updateAdapter(journals);
                        }else{
                            mJournalDisplayView.noJournals();
                        }
                    }
                });
    }

    public void bind(Context context, JournalDisplayView journalDisplayView){
        Context context1 = context;
        mJournalLab = JournalLab.get(context1);
        mJournalDisplayView = journalDisplayView;
    }

}

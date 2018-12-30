package com.software.tongji.easygo.JournalDisplayMvp;

import android.content.Context;
import android.os.AsyncTask;

import com.software.tongji.easygo.bean.Journal;
import com.software.tongji.easygo.bean.JournalLab;


import java.util.List;
import java.util.Observable;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JournalDisplayPresenter {
    private JournalDisplayView mJournalDisplayView;
    private Context mContext;
    private JournalLab mJournalLab;
    private List<Journal> mJournals;

    public void addJournal(Journal journal){
        mJournalLab.addJournal(journal);
    }

    public void deleteJournal(Journal journal){
        mJournalLab.deleteJournal(journal);
    }

    public List<Journal> getJournals(){

        mJournalDisplayView.showLoadingDialog();
        rx.Observable.create(new rx.Observable.OnSubscribe<List<Journal>>() {
            @Override
            public void call(Subscriber<? super List<Journal>> subscriber) {
                List<Journal> journals = mJournalLab.getJournalList();
                subscriber.onNext(journals);
                subscriber.onCompleted();
            }
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
                mJournalDisplayView.updateAdapter(journals);
            }
        });
        return mJournals;
    }

    public void bind(Context context, JournalDisplayView journalDisplayView){
        mContext = context;
        mJournalLab = JournalLab.get(mContext);
        mJournalDisplayView = journalDisplayView;
    }

}

package com.software.tongji.easygo.JournalDisplayMvp;

import android.content.Context;
import android.os.AsyncTask;

import com.software.tongji.easygo.bean.Journal;
import com.software.tongji.easygo.bean.JournalLab;
import com.software.tongji.easygo.net.ApiService;
import com.software.tongji.easygo.net.BaseResponse;
import com.software.tongji.easygo.net.DefaultObserver;
import com.software.tongji.easygo.net.RetrofitServiceManager;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JournalDisplayPresenter {
    private JournalDisplayView mJournalDisplayView;
    private Context mContext;
    private JournalLab mJournalLab;
    private List<Journal> mJournals;

    class  MyAsyncTask extends AsyncTask<String,List<Journal>,List<Journal>> {

        @Override
        protected List<Journal> doInBackground(String... strings) {
            return mJournalLab.getJournalList();
        }

        @Override
        protected void onPostExecute(List<Journal> journals) {
            mJournals = journals;
            mJournalDisplayView.updateAdapter(journals);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mJournalDisplayView.showLoadingDialog();
        }
    }

    public void addJournal(Journal journal){
        mJournalLab.addJournal(journal);
    }

    public void deleteJournal(Journal journal){
        mJournalLab.deleteJournal(journal);
    }

    public List<Journal> getJournals(){

        new MyAsyncTask().execute();
        mJournalDisplayView.dismissLoadingDialog();
        return mJournals;
    }

    public void bind(Context context, JournalDisplayView journalDisplayView){
        mContext = context;
        mJournalLab = JournalLab.get(mContext);
        mJournalDisplayView = journalDisplayView;
    }

}

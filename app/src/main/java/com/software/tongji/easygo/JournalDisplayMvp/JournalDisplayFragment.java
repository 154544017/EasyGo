package com.software.tongji.easygo.JournalDisplayMvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Journal;
import com.software.tongji.easygo.journaledit.JournalEditActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalDisplayFragment extends Fragment implements JournalDisplayView {

    public static final int REQUEST_CODE_NEW_JOURNAL = 1;

    @BindView(R.id.journal_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.journal_search_view)
    SearchView mSearchView;
    @BindView(R.id.journal_add_button)
    FloatingActionButton mAddJournalButton;

    private JournalAdapter mJournalAdapter;
    private MaterialDialog mDialog;
    private JournalDisplayPresenter mJournalDisplayPresenter = new JournalDisplayPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.journal_display_fragment,container,false);

        ButterKnife.bind(this, view);
        mJournalDisplayPresenter.bind(getContext(),this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mJournalAdapter = new JournalAdapter();
        mRecyclerView.setAdapter(mJournalAdapter);

        mSearchView.setQueryHint("Search journal");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mAddJournalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Journal journal = new Journal();
                mJournalDisplayPresenter.addJournal(journal);
                Intent intent = JournalEditActivity.newIntent(getContext(), journal.getId());
                startActivityForResult(intent, REQUEST_CODE_NEW_JOURNAL);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CODE_NEW_JOURNAL){
           mJournalDisplayPresenter.getJournals();
        }
    }

    @Override
    public Context getJournalListContext() {
        return getContext();
    }

    @Override
    public void showLoadingDialog() {
        mDialog = new MaterialDialog.Builder(getContext())
                .title(R.string.app_name)
                .content("Please Wait...")
                .progress(true, 0)
                .show();
    }

    @Override
    public void dismissLoadingDialog() {
        mDialog.dismiss();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateAdapter(List<Journal> journalList) {
        mJournalAdapter.updateItemData(journalList);
        mJournalAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        mJournalDisplayPresenter.getJournals();
    }

}

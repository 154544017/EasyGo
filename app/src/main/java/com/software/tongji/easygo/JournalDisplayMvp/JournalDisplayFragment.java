package com.software.tongji.easygo.JournalDisplayMvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Journal;
import com.software.tongji.easygo.bean.JournalLab;
import com.software.tongji.easygo.journaledit.JournalEditActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalDisplayFragment extends Fragment implements JournalDisplayView{

    public static final int REQUEST_CODE_NEW_JOURNAL = 1;

    @BindView(R.id.journal_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.journal_search_view)
    SearchView mSearchView;
    @BindView(R.id.journal_add_button)
    FloatingActionButton mAddJournalButton;

    private JournalAdapter mJournalAdapter;

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
        mJournalDisplayPresenter.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*List<Journal> journals = new ArrayList<>();
        for(int i = 0;i<3;i++){
        Journal journal = new Journal();
        journals.add(journal);}*/
        mJournalAdapter = new JournalAdapter(getContext(), JournalLab.get(getContext()).getJournalList());
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
                Intent intent = new Intent(getActivity(), JournalEditActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NEW_JOURNAL);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CODE_NEW_JOURNAL){
            String title = data.getStringExtra(JournalEditActivity.NEW_JOURNAL_TITLE);
            String date = data.getStringExtra(JournalEditActivity.NEW_JOURNAL_DATE);
            String location = data.getStringExtra(JournalEditActivity.NEW_JOURNAL_LOCATION);
            String friends = data.getStringExtra(JournalEditActivity.NEW_JOURNAL_FRIENDS);
            String content = data.getStringExtra(JournalEditActivity.NEW_JOURNAL_CONTENT);
            Journal journal = new Journal(title, date, location, friends, content);
            mJournalDisplayPresenter.addJournal(journal);
            mJournalAdapter.notifyItemInserted(0);
        }
    }

    @Override
    public Context getJournalListContetx() {
        return getContext();
    }
}

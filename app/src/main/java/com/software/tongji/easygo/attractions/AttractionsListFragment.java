package com.software.tongji.easygo.attractions;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttractionsListFragment extends Fragment {

    @BindView(R.id.attractions_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_tool_bar)
    Toolbar mSearch;

    private AttractionsAdapter mAttractionsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mSearch);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<String> name = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            name.add("AttractionsName");
        }
        mAttractionsAdapter = new AttractionsAdapter(name);
        mRecyclerView.setAdapter(mAttractionsAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.attractions_search, menu);

        MenuItem item = menu.findItem(R.id.attractions_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}

package com.software.tongji.easygo.JournalModule.JournalDisplay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.Bean.Journal;
import com.software.tongji.easygo.InputTipModule.InputProvince.InputProvinceAdapter;
import com.software.tongji.easygo.JournalModule.JournalEdit.JournalEditActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalDisplayFragment extends Fragment implements JournalDisplayView {

    public static final int REQUEST_CODE_NEW_JOURNAL = 1;

    @BindView(R.id.no_journals)
    TextView mNoJournalsView;
    @BindView(R.id.provinces_list)
    ListView mProvinceTipView;
    @BindView(R.id.journal_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.journal_search_view)
    SearchView mSearchView;
    @BindView(R.id.journal_add_button)
    FloatingActionButton mAddJournalButton;

    //用于判断该界面是否由成就界面跳转，并记录过滤的省份
    private static final String NO_DEFAULT = "is_not_jump_from_map";
    private String mProvinceArg = NO_DEFAULT;
    private JournalAdapter mJournalAdapter;
    private boolean isFirstResume = true;
    private MaterialDialog mDialog;
    private InputProvinceAdapter mProvinceListAdapter;
    private JournalDisplayPresenter mJournalDisplayPresenter = new JournalDisplayPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProvinceListAdapter = new InputProvinceAdapter(getContext());
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

        mProvinceTipView.setAdapter(mProvinceListAdapter);
        initSearchView();

        mAddJournalButton.setOnClickListener(view1 -> {
            Intent intent = JournalEditActivity.newIntent(getContext(), "add");
            startActivityForResult(intent, REQUEST_CODE_NEW_JOURNAL);
        });
        mJournalDisplayPresenter.getJournals();
        return view;
    }

    void initSearchView(){
        mSearchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                mProvinceTipView.setVisibility(View.VISIBLE);
                mProvinceTipView.bringToFront();
            }else {
                mProvinceTipView.setVisibility(View.GONE);
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mProvinceListAdapter.getFilter().filter(newText);
                return true;
            }
        });
        //设置SearchView默认为展开显示
        mSearchView.setIconified(false);
        mSearchView.onActionViewExpanded();
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setSubmitButtonEnabled(false);

        //初始化listView
        mProvinceTipView.setOnItemClickListener((parent, view, position, id) -> {
            String province = (String)parent.getItemAtPosition(position);
            if(mNoJournalsView.getVisibility() == View.VISIBLE){
                mNoJournalsView.setVisibility(View.GONE);
            }
            mSearchView.setQueryHint(province);
            mSearchView.clearFocus();
            if(province.equals("显示全部")){
               mJournalDisplayPresenter.getJournals();
            } else {
                mJournalDisplayPresenter.getJournalsByProvince(province);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CODE_NEW_JOURNAL){
            //当新添加完游记后更新列表
           mJournalDisplayPresenter.getJournals();
        }
    }

    @Override
    public void showLoadingDialog() {
        if (mDialog == null || mDialog.isCancelled()) {
            mDialog = new MaterialDialog.Builder(getContext())
                    .title(R.string.load_journals)
                    .content("Please Wait...")
                    .progress(true, 0)
                    .show();
        }
    }

    @Override
    public void dismissLoadingDialog() {
        if (!mDialog.isCancelled()) {
            mDialog.dismiss();
        }
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

    //显示没有游记的View
    @Override
    public void noJournals() {
        List<Journal> journals = new ArrayList<>();
        updateAdapter(journals);
        mNoJournalsView.setVisibility(View.VISIBLE);
        mNoJournalsView.bringToFront();
    }

    @Override
    public void onResume() {
        super.onResume();
        //fragment添加到navigation时会第一次调用onResume，此时不获取游记，只有当跳转到该fragment调用onResume才更新
        if(isFirstResume){
            isFirstResume = false;
            return;
        }
        mJournalDisplayPresenter.getJournals();
        Log.e("fragment", "onResume ");
    }

    //当navigation显示该界面时，根据记录的province进行游记过滤
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("fragment", "onHidden: " + String.valueOf(hidden));
        if(!hidden){
            if(mProvinceArg.equals(NO_DEFAULT)){
                mJournalDisplayPresenter.getJournals();
            }else{
                mSearchView.setQueryHint(mProvinceArg);
                mJournalDisplayPresenter.getJournalsByProvince(mProvinceArg);
                mProvinceArg = NO_DEFAULT;
            }
        }
    }

    public void setDefaultProvince(String province){
        mProvinceArg = province;
    }
}

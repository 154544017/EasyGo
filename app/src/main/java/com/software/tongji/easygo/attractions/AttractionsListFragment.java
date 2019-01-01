package com.software.tongji.easygo.attractions;


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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Attraction;
import com.software.tongji.easygo.inputProvince.InputProvinceAdapter;
import com.software.tongji.easygo.shake.ShakeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttractionsListFragment extends Fragment implements AttractionDisplayContract.AttractionDisplayView {

    @BindView(R.id.no_attractions)
    TextView mNoAttractionView;
    @BindView(R.id.attractions_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.provinces_list)
    ListView mProvinceTipView;
    @BindView(R.id.attractions_search_view)
    SearchView mSearchView;
    @BindView(R.id.shake_button)
    FloatingActionButton mShakeButton;

    private static final String NO_DEFAULT = "is_not_jump_from_map";
    private MaterialDialog mDialog;
    private AttractionDisplayPresenterImpl mPresenter;
    private AttractionsAdapter mAttractionsAdapter;
    private InputProvinceAdapter mProvinceListAdapter;
    private String mProvinceArg = NO_DEFAULT;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AttractionDisplayPresenterImpl(this);
        mProvinceListAdapter = new InputProvinceAdapter(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAttractionsAdapter = new AttractionsAdapter();
        mRecyclerView.setAdapter(mAttractionsAdapter);

        mProvinceTipView.setAdapter(mProvinceListAdapter);
        initSearchView();
        mPresenter.getAllAttractions();

        mShakeButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ShakeActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void initSearchView(){
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
            if(mNoAttractionView.getVisibility() == View.VISIBLE){
                mNoAttractionView.setVisibility(View.GONE);
            }
            mSearchView.setQueryHint(province);
            mSearchView.clearFocus();
            if(province.equals("显示全部")){
                mPresenter.getAllAttractions();
            } else {
                mPresenter.getAttractionsByProvince(province);
            }
        });
    }


    @Override
    public void showLoading() {
        mDialog = new MaterialDialog.Builder(getContext())
                .title(R.string.load_attractions)
                .content("Please Wait...")
                .progress(true, 0)
                .show();
    }

    @Override
    public void dismissLoading() {
        mDialog.dismiss();
    }

    @Override
    public void refreshUI(List<Attraction> attractionList) {
        mNoAttractionView.setVisibility(View.GONE);
        mAttractionsAdapter.updateItemData(attractionList);
        mAttractionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void findNoAttractions() {
        List<Attraction> attractions = new ArrayList<>();
        refreshUI(attractions);
        mNoAttractionView.setVisibility(View.VISIBLE);
        mNoAttractionView.bringToFront();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            if(!mProvinceArg.equals(NO_DEFAULT)){
                mSearchView.setQueryHint(mProvinceArg);
                mPresenter.getAttractionsByProvince(mProvinceArg);
                mProvinceArg = NO_DEFAULT;
            }
        }
    }

    public void setDefaultProvince(String province){
        mProvinceArg = province;
    }
}

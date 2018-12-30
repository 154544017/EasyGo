package com.software.tongji.easygo.attractions;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.Attraction;
import com.software.tongji.easygo.inputProvince.InputProvinceAdapter;
import com.software.tongji.easygo.utils.MapHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttractionsListFragment extends Fragment implements AttractionDisplayContract.AttractionDisplayView {

    @BindView(R.id.search_linear_layout)
    LinearLayout mLinearLayout;
    @BindView(R.id.attractions_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.provinces_list)
    ListView mProvinceTipView;
    @BindView(R.id.attractions_search_view)
    SearchView mSearchView;

    private MaterialDialog mDialog;
    private AttractionDisplayPresenterImpl mPresenter;
    private AttractionsAdapter mAttractionsAdapter;
    private InputProvinceAdapter mProvinceListAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AttractionDisplayPresenterImpl(this);
        mProvinceListAdapter = new InputProvinceAdapter(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAttractionsAdapter = new AttractionsAdapter();
        mRecyclerView.setAdapter(mAttractionsAdapter);

        mProvinceTipView.setAdapter(mProvinceListAdapter);
        initSearchView();
        mPresenter.getAllAttractions();
        return view;
    }

    void initSearchView(){
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mProvinceTipView.setVisibility(View.VISIBLE);
                    mProvinceTipView.bringToFront();
                }else {
                    mProvinceTipView.setVisibility(View.GONE);
                }
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
        mProvinceTipView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String province = (String)parent.getItemAtPosition(position);
                mSearchView.setQueryHint(province);
                mSearchView.clearFocus();
                mPresenter.getAttractionsByProvince(MapHelper.provincePinYin.get(province));
            }
        });
    }


    @Override
    public void showLoading() {
        mDialog = new MaterialDialog.Builder(getContext())
                .title(R.string.app_name)
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
        mAttractionsAdapter.updateItemData(attractionList);
        mAttractionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }

}

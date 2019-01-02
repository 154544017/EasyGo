package com.software.tongji.easygo.MapMvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.software.tongji.easygo.ProvinceDialog.ProvinceDialogFragment;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.JournalLab;
import com.software.tongji.easygo.bean.MyMap;
import com.software.tongji.easygo.bean.ProvinceLab;
import com.software.tongji.easygo.bean.TourLab;
import com.software.tongji.easygo.view.MyMapView;

import butterknife.BindView;

public class MapFragment extends Fragment implements MapContract.MapView {

    private TextView mProvinces;
    private TextView mJournals;

    private static final int REQUEST_LOCKED = 0;
    private static final String DIALOG_PROVINCE = "dialog_province";
    private MapContract.MapPresenter mPresenter;
    private MyMapView mMapView;
    private MyMap mMyMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MapPresenterImpl(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.achievement_map_fragment,container,false);
        mMapView = view.findViewById(R.id.achievement_map);
        mProvinces = view.findViewById(R.id.province_achievement);
        mJournals = view.findViewById(R.id.journal_achievement);
        mMapView.setOnChoseProvince(provinceName -> {
            Toast.makeText(getActivity(), provinceName,Toast.LENGTH_SHORT).show();
            FragmentManager manager = getActivity().getSupportFragmentManager();
            ProvinceDialogFragment fragment = ProvinceDialogFragment.newInstance(provinceName);
            fragment.setTargetFragment(MapFragment.this, REQUEST_LOCKED);
            fragment.show(manager,DIALOG_PROVINCE);
        });
        mMyMap = mPresenter.getMap();
        mMapView.setMap(mMyMap);
        updateAchievements();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_LOCKED){
                String provinceName = data.getStringExtra(ProvinceDialogFragment.EXTRA_NAME);
                mPresenter.changeProvinceState(mMyMap,provinceName);
            }
        }
    }

    public void updateAchievements(){
        int provinceCount = ProvinceLab.getProvinceLab(getContext()).getUnlockedSize();
        int journalCount = JournalLab.get(getContext()).size();
        if(provinceCount > 0){
            mProvinces.setText("您已经解锁了" + String.valueOf(provinceCount) + "个省份");
        }
        if(journalCount > 0){
            mJournals.setText("您已经编写了" + String.valueOf(journalCount) + "篇游记");
        }
    }

    @Override
    public void refreshUI() {
        mMapView.changeMapColors();
        updateAchievements();
    }

    @Override
    public void showError() {

    }

    @Override
    public void setPresenter(MapContract.MapPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAchievements();
    }
}

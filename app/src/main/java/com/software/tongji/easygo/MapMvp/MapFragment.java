package com.software.tongji.easygo.MapMvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.software.tongji.easygo.ProvinceDialog.ProvinceDialogFragment;
import com.software.tongji.easygo.R;
import com.software.tongji.easygo.bean.MyMap;
import com.software.tongji.easygo.view.MyMapView;

public class MapFragment extends Fragment implements MapContract.MapView {
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
        mMapView.setOnChoseProvince(new MyMapView.OnProvinceClickListener(){
            @Override
            public void onChose(String provinceName) {
                Toast.makeText(getActivity(), provinceName,Toast.LENGTH_SHORT).show();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                ProvinceDialogFragment fragment = ProvinceDialogFragment.newInstance(provinceName);
                fragment.setTargetFragment(MapFragment.this, REQUEST_LOCKED);
                fragment.show(manager,DIALOG_PROVINCE);
            }
        });
        mMyMap = mPresenter.getMap();
        mMapView.setMap(mMyMap);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }else{
            if(requestCode == REQUEST_LOCKED){
                String provinceName = data.getStringExtra(ProvinceDialogFragment.EXTRA_NAME);
                mPresenter.changeProvinceState(mMyMap,provinceName,true);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void refreshUI() {
        mMapView.changeMapColors();
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

    public MyMap getMyMap(){
        return mMyMap;
    }
}

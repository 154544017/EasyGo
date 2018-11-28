package com.software.tongji.easygo.MapMvp;

import android.content.Context;

import com.software.tongji.easygo.bean.MyMap;
import com.software.tongji.easygo.bean.ProvinceLab;

public class MapPresenterImpl implements MapContract.MapPresenter {
    private MapContract.MapView mMapView;
    private Context mContext;

    public MapPresenterImpl(Context context,MapContract.MapView mapView) {
        mContext = context;
        mMapView = mapView;
        mapView.setPresenter(this);
    }

    @Override
    public MyMap getMap() {
        MyMap myMap;
        myMap = ProvinceLab.getProvinceLab(mContext).getMap();
        return myMap;
    }


    @Override
    public void changeProvinceState(MyMap myMap, String provinceName, boolean isLocked) {
        ProvinceLab.getProvinceLab(mContext).updateProvinceState(myMap, provinceName, false);
        mMapView.refreshUI();
    }

    @Override
    public void start() {

    }
}


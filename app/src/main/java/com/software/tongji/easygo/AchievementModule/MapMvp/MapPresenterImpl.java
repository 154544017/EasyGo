package com.software.tongji.easygo.AchievementModule.MapMvp;

import android.content.Context;

import com.software.tongji.easygo.Bean.MyMap;
import com.software.tongji.easygo.Bean.ProvinceLab;

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
    public void changeProvinceState(MyMap myMap, String provinceName) {
        ProvinceLab.getProvinceLab(mContext).updateProvinceState(myMap, provinceName, false);
        mMapView.refreshUI();
    }

}


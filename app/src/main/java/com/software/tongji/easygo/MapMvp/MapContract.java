package com.software.tongji.easygo.MapMvp;

import com.software.tongji.easygo.basic.BasePresenter;
import com.software.tongji.easygo.basic.BaseView;
import com.software.tongji.easygo.bean.MyMap;

public interface MapContract {

    interface MapPresenter extends BasePresenter {
        MyMap getMap();
        void changeProvinceState(MyMap myMap, String provinceName);
    }

    interface MapView extends BaseView<MapPresenter> {
        void refreshUI();
        void showError();
    }
}

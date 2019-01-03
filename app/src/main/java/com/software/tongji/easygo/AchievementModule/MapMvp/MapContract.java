package com.software.tongji.easygo.AchievementModule.MapMvp;

import com.software.tongji.easygo.Bean.MyMap;

public interface MapContract {

    interface MapPresenter{
        MyMap getMap();
        void changeProvinceState(MyMap myMap, String provinceName);
    }

    interface MapView{
        void setPresenter(MapPresenter presenter);
        void refreshUI();
        void showError();
    }
}

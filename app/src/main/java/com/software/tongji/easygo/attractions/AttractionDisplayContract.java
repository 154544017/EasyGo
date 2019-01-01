package com.software.tongji.easygo.attractions;

import com.software.tongji.easygo.bean.Attraction;

import java.util.List;

public class AttractionDisplayContract {
    interface AttractionDisplayPresenter {
        void getAllAttractions();
        void getAttractionsByProvince(String query);

    }

    interface AttractionDisplayView {
        void showLoading();
        void dismissLoading();
        void refreshUI(List<Attraction> attractionList);
        void showError(String message);
        void findNoAttractions();
    }
}

package com.software.tongji.easygo.AttractinonsModule.AttractionsList;

import com.software.tongji.easygo.Bean.Attraction;

import java.util.List;

class AttractionDisplayContract {
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

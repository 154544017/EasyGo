package com.software.tongji.easygo.AttractinonsModule.AttractionsList;

import com.software.tongji.easygo.AttractinonsModule.AttractionsList.AttractionDisplayContract.AttractionDisplayPresenter;
import com.software.tongji.easygo.AttractinonsModule.AttractionsList.AttractionDisplayContract.AttractionDisplayView;
import com.software.tongji.easygo.Bean.Attraction;
import com.software.tongji.easygo.Net.ApiService;
import com.software.tongji.easygo.Net.BaseResponse;
import com.software.tongji.easygo.Net.DefaultObserver;
import com.software.tongji.easygo.Net.RetrofitServiceManager;
import com.software.tongji.easygo.Utils.MapHelper;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AttractionDisplayPresenterImpl implements AttractionDisplayPresenter {

    private final AttractionDisplayView mAttractionDisplayView;

    public AttractionDisplayPresenterImpl(AttractionDisplayView attractionDisplayView){
        mAttractionDisplayView = attractionDisplayView;
    }

    @Override
    public void getAllAttractions() {
        mAttractionDisplayView.showLoading();
        RetrofitServiceManager.getInstance()
                .getRetrofit()
                .create(ApiService.class)
                .getAllAttractions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<List<Attraction>>>(){

                    @Override
                    public void onSuccess(Object result) {
                        mAttractionDisplayView.dismissLoading();
                        List<Attraction> attractions = (List<Attraction>) result;

                        if(attractions.size() > 0){
                            mAttractionDisplayView.refreshUI(attractions);
                        }
                    }

                    @Override
                    public void onFail(String message) {
                        mAttractionDisplayView.dismissLoading();
                        mAttractionDisplayView.showError(message);
                    }

                    @Override
                    public void onError(String message) {
                        mAttractionDisplayView.dismissLoading();
                        mAttractionDisplayView.showError(message);
                    }
                });

    }

    @Override
    public void getAttractionsByProvince(String query) {
        mAttractionDisplayView.showLoading();
        RetrofitServiceManager.getInstance()
                .getRetrofit()
                .create(ApiService.class)
                .getAttractionsByProvince(MapHelper.provincePinYin.get(query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<List<Attraction>>>(){

                    @Override
                    public void onSuccess(Object result) {
                        mAttractionDisplayView.dismissLoading();
                        List<Attraction> attractions = (List<Attraction>) result;
                        if(attractions.size() > 0){
                            mAttractionDisplayView.refreshUI(attractions);
                        }
                    }

                    @Override
                    public void onFail(String message) {
                        mAttractionDisplayView.dismissLoading();
                        mAttractionDisplayView.findNoAttractions();
                    }

                    @Override
                    public void onError(String message) {
                        mAttractionDisplayView.dismissLoading();
                        mAttractionDisplayView.showError(message);
                    }
                });
    }
}

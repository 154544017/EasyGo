package com.software.tongji.easygo.attractions;

import com.software.tongji.easygo.attractions.AttractionDisplayContract.AttractionDisplayPresenter;
import com.software.tongji.easygo.attractions.AttractionDisplayContract.AttractionDisplayView;
import com.software.tongji.easygo.bean.Attraction;
import com.software.tongji.easygo.net.ApiService;
import com.software.tongji.easygo.net.BaseResponse;
import com.software.tongji.easygo.net.DefaultObserver;
import com.software.tongji.easygo.net.RetrofitServiceManager;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AttractionDisplayPresenterImpl implements AttractionDisplayPresenter {

    private AttractionDisplayView mAttractionDisplayView;

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
                .getAttractionsByProvince(query)
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
}

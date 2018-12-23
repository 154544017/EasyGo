package com.software.tongji.easygo.login;


import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.google.gson.JsonObject;
import com.software.tongji.easygo.bean.Schedule;
import com.software.tongji.easygo.bean.UserData;
import com.software.tongji.easygo.net.ApiService;
import com.software.tongji.easygo.net.BaseResponse;
import com.software.tongji.easygo.net.DefaultObserver;
import com.software.tongji.easygo.net.RetrofitServiceManager;
import com.software.tongji.easygo.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.internal.operators.DeferredScalarSubscriber;
import rx.schedulers.Schedulers;

public class LoginPresenter {

    private LoginView mLoginView;

    public void bind(LoginView loginView){
        mLoginView = loginView;
    }

    public void unbind(){mLoginView = null;}

    public void signUp(){
        mLoginView.openSignUp();
    }

    public void login(){
        mLoginView.openLogin();
    }

    public void forgotPassword(){
        mLoginView.forgotPassword();
    }

    public void resendResetCode(String email, Handler handler){
        okPasswordResetRequest(email, handler);
        mLoginView.resendResetCode();
    }

    public void newPasswordInput() {
        mLoginView.newPasswordInput();
    }

    public void okSignUp(String username, String email,
                         String password, Handler handler) {
        mLoginView.showLoadingDialog();
        RetrofitServiceManager.getInstance()
                .getRetrofit()
                .create(ApiService.class)
                .signUp(HttpUtils.getFormDataRequest(username),HttpUtils.getFormDataRequest(password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<UserData>>() {
                    @Override
                    public void onSuccess(Object result) {
                        mLoginView.openLogin();
                        mLoginView.setLoginEmail(username);
                        mLoginView.showMessage("注册成功！请登录");
                    }

                    @Override
                    public void onFail(String message) {
                        mLoginView.showError(message);
                    }

                    @Override
                    public void onError(String message) {
                        mLoginView.showError(message);
                    }
                });
        mLoginView.dismissLoadingDialog();
    }

    public void okLogin(String email, String password,Handler handler){
        mLoginView.showLoadingDialog();
        RetrofitServiceManager.getInstance()
                .getRetrofit()
                .create(ApiService.class)
                .login(HttpUtils.getFormDataRequest(email),HttpUtils.getFormDataRequest(password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<UserData>>() {
                    @Override
                    public void onSuccess(Object result) {
                        UserData data = (UserData)result;
                        mLoginView.rememberUserInfo(data.getToken(),data.getUser());
                        mLoginView.startMainActivity();
                    }

                    @Override
                    public void onFail(String message) {
                        mLoginView.showError(message);
                    }

                    @Override
                    public void onError(String message) {
                        mLoginView.showError(message);
                    }
                });
        mLoginView.dismissLoadingDialog();

    }

    public void okPasswordResetRequest(String email, Handler handler){
        handler.post(()-> mLoginView.showLoadingDialog());

    }

    public void okPasswordResetConfirm(String email){
        mLoginView.confirmPasswordReset(email);
    }

    public void okPasswordReset(String email, String code,
                                String newPassword, Handler handler){
        handler.post(()->mLoginView.showLoadingDialog());


    }



}

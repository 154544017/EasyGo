package com.software.tongji.easygo.Login;


import android.os.Handler;
import com.software.tongji.easygo.Bean.UserData;
import com.software.tongji.easygo.Net.ApiService;
import com.software.tongji.easygo.Net.BaseResponse;
import com.software.tongji.easygo.Net.DefaultObserver;
import com.software.tongji.easygo.Net.RetrofitServiceManager;
import com.software.tongji.easygo.Utils.HttpUtils;
import rx.android.schedulers.AndroidSchedulers;
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

    //注册请求
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
                        mLoginView.setUserName(username);
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

    //登陆请求
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

    //发送重置密码验证码（未完成）
    public void okPasswordResetRequest(String email, Handler handler){
        handler.post(()-> mLoginView.showLoadingDialog());

    }

    //验证重置密码验证码（未完成）
    public void okPasswordResetConfirm(String email){
        mLoginView.confirmPasswordReset(email);
    }

    //重置密码（未完成）
    public void okPasswordReset(String email, String code,
                                String newPassword, Handler handler){
        handler.post(()->mLoginView.showLoadingDialog());


    }



}

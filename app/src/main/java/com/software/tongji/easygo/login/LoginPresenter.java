package com.software.tongji.easygo.login;


import android.os.Handler;

public class LoginPresenter {

    private LoginView mLoginView;

    public void bind(LoginView loginView){
        mLoginView = loginView;
    }

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

    }

    public void okLogin(String email, String password,Handler handler){
        mLoginView.showLoadingDialog();

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

package com.software.tongji.easygo.login;

public interface LoginView {

    void showLoadingDialog();

    void dismissLoadingDialog();

    void openSignUp();

    void openLogin();

    void setLoginEmail(String email);

    void showMessage(String message);

    void forgotPassword();

    void openResetPin(String email);

    void resendResetCode();

    void newPasswordInput();

    void confirmPasswordReset(String email);

}

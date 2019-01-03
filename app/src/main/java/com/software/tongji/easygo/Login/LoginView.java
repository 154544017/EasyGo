package com.software.tongji.easygo.Login;

public interface LoginView {

    void rememberUserInfo(String token, String email);

    void startMainActivity();

    void showLoadingDialog();

    void showError(String error);

    void dismissLoadingDialog();

    void checkUserSession();

    void openSignUp();

    void openLogin();

    void setUserName(String userName);

    void showMessage(String message);

    void forgotPassword();

    void resendResetCode();

    void newPasswordInput();

    void confirmPasswordReset(String email);

}

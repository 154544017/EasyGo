package com.software.tongji.easygo.login;

public interface LoginView {

    void rememberUserInfo(String token, String email);

    void startMainActivity();

    void showLoadingDialog();

    void showError(String error);

    void dismissLoadingDialog();

    void checkUserSession();

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

package com.software.tongji.easygo.login;


import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.google.gson.JsonObject;

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
        String url = Uri.parse("http://47.110.63.70/api/user/register")
                .buildUpon()
                .appendQueryParameter("name",username)
                .appendQueryParameter("password",password)
                .build()
                .toString();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Log.e("signup","url: "+ url);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mLoginView != null){
                            mLoginView.showError("连接失败");
                            Log.e("signup","onFailure");
                            mLoginView.dismissLoadingDialog();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = Objects.requireNonNull(response.body().string());
                handler.post(()->{
                    try{
                        JSONObject responseJsonObject = new JSONObject(res);
                        Log.e("login","response:"+res);
                        String state = responseJsonObject.getString("desc");
                        if(state.equals("OK")){
                            mLoginView.openLogin();
                            mLoginView.setLoginEmail(email);
                            mLoginView.showMessage("注册成功！请登录");
                        }else{
                            mLoginView.showError(state);
                        }
                        mLoginView.dismissLoadingDialog();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                });

            }
        });


    }

    public void okLogin(String email, String password,Handler handler){
        mLoginView.showLoadingDialog();
        String url = Uri.parse("http://47.110.63.70/api/user/login")
                .buildUpon()
                .appendQueryParameter("name",email)
                .appendQueryParameter("password",password)
                .build()
                .toString();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Log.e("login","url: "+ url);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mLoginView != null){
                            mLoginView.showError("连接失败");
                            Log.e("login","onFailure");
                            mLoginView.dismissLoadingDialog();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = Objects.requireNonNull(response.body().string());
                handler.post(()->{
                    try{
                        JSONObject responseJsonObject = new JSONObject(res);
                        Log.e("login","response:"+res);
                        String state = responseJsonObject.getString("desc");
                        if(state.equals("登录成功")){
                            JSONObject userInfo = responseJsonObject.getJSONObject("data");
                            String userName = userInfo.getString("user");
                            String token = userInfo.getString("token");
                            mLoginView.rememberUserInfo(token,userName);
                            mLoginView.startMainActivity();
                        }else{
                            mLoginView.showError(state);
                        }
                        mLoginView.dismissLoadingDialog();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                });

            }
        });
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

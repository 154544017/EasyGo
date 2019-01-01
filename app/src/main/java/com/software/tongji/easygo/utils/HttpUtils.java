package com.software.tongji.easygo.utils;

import android.util.Log;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HttpUtils {
    public static RequestBody getFormDataRequest(String data){
        return RequestBody.create(MediaType.parse("multipart/form-data"),data);
    }

    public static String getProvinceDisplayImageUrl(String provinceName){
        String url = "http://47.110.63.70/api/image/province/" + provinceName + ".jpg";
        Log.e("url:", url);
        return url;
    }
}

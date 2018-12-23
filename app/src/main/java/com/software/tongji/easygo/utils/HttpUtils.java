package com.software.tongji.easygo.utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HttpUtils {
    public static RequestBody getFormDataRequest(String data){
        return RequestBody.create(MediaType.parse("multipart/form-data"),data);
    }
}

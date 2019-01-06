package com.software.tongji.easygo.Utils;

import android.util.Log;

import okhttp3.MediaType;
import okhttp3.RequestBody;

//网络请求有关工具
public class HttpUtils {
    //将data包装成RequestBody
    public static RequestBody getFormDataRequest(String data){
        return RequestBody.create(MediaType.parse("multipart/form-data"),data);
    }
    //获取省份展示的图片路径，如"http://47.110.63.70/api/image/province/beijing.jpg"
    public static String getProvinceDisplayImageUrl(String provinceName){
        String url = "http://47.110.63.70/api/image/province/" + provinceName + ".jpg";
        Log.e("url:", url);
        return url;
    }
}

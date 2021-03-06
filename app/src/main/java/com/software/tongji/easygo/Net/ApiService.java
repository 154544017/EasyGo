package com.software.tongji.easygo.Net;

import com.software.tongji.easygo.Bean.Attraction;
import com.software.tongji.easygo.Bean.Journal;
import com.software.tongji.easygo.Bean.UserData;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiService {

    String API_SERVER_URL = "http://47.110.63.70/api/";

    @Multipart
    @POST("user/login")
    Observable<BaseResponse<UserData>> login(@Part("name") RequestBody userName, @Part("password") RequestBody password);

    @Multipart
    @POST("user/register")
    Observable<BaseResponse<UserData>> signUp(@Part("name") RequestBody userName, @Part("password") RequestBody password);

    @GET("user/getInfo/{user}")
    Observable<BaseResponse<UserData>> getInfo(@Path("user") String userName);

    @GET("journal/get/{user}")
    Observable<BaseResponse<List<Journal>>> getJournals(@Path("user") String userName);

    @Multipart
    @POST("journal/post/{user}")
    Observable<BaseResponse<Void>> postJournals(@Path("user") String userName,
                                                @Part("journal") RequestBody journalJson,
                                                @Part MultipartBody.Part part);

    @POST("journal/delete/{user}")
    Observable<BaseResponse<Void>> deleteJournal(@Path("user") String userName,
                                                 @Body FormBody body);

    @GET("attraction/getAll")
    Observable<BaseResponse<List<Attraction>>> getAllAttractions();

    @GET("attraction/{provinceName}")
    Observable<BaseResponse<List<Attraction>>> getAttractionsByProvince(@Path("provinceName") String provinceName);

    //获取随机景点推荐
    @GET("attraction/getRandom")
    Observable<BaseResponse<Attraction>> getRandomAttraction();

}

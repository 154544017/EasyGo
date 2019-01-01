package com.software.tongji.easygo.net;

import android.net.ParseException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;
import rx.Observer;

import static com.software.tongji.easygo.net.DefaultObserver.ExceptionReason.CONNECT_ERROR;
import static com.software.tongji.easygo.net.DefaultObserver.ExceptionReason.CONNECT_TIMEOUT;
import static com.software.tongji.easygo.net.DefaultObserver.ExceptionReason.PARSE_ERROR;
import static com.software.tongji.easygo.net.DefaultObserver.ExceptionReason.UNKNOWN_ERROR;

public abstract class DefaultObserver<T extends BaseResponse> implements Observer<T> {

    //  Activity 是否在执行onStop()时取消订阅
    private boolean isAddInStop = false;



    @Override
    public void onCompleted() {

    }



    @Override
    public void onNext(@NonNull T t) {
        if(t.isSuccess()){
            onSuccess(t.getResult());
        }else{
            onFail(t.getDesc());
        }
    }


    @Override
    public void onError(@NonNull Throwable e) {
        Log.e("DefaultObserver",e.getMessage());
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(PARSE_ERROR);
        } else {
            onException(UNKNOWN_ERROR);
        }
    }


    /**
     * 请求成功
     *
     * @param result 服务器返回的数据
     */
    abstract public void onSuccess(Object result);

    /**
     * 服务器返回数据，但响应码不为1000
     *
     * @param message 错误信息
     */
    abstract public void onFail(String message);

    abstract public void onError(String message);

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                onError("网络链接错误");
                break;

            case CONNECT_TIMEOUT:
                onError("连接超时");
                break;

            case BAD_NETWORK:
                onError("网络状况差");
                break;

            case PARSE_ERROR:
                onError("解析失败");
                break;

            case UNKNOWN_ERROR:
            default:
                onError("未知错误");
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}


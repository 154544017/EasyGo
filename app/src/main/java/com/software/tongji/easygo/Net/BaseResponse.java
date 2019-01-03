package com.software.tongji.easygo.Net;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    private String desc;
    private int status;
    @SerializedName("data")
    private T result;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess(){
        return status == 1000;
    }
}

package com.my.booze.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceOrderResponseModel {
    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private int status;

    public PlaceOrderResponseModel() {

    }

    public PlaceOrderResponseModel(String msg, int status) {
        this.msg = msg;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}

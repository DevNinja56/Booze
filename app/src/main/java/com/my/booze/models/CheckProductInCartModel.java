package com.my.booze.models;

import com.google.gson.annotations.SerializedName;

public class CheckProductInCartModel {

    @SerializedName("data")
    private int data;
    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private int status;

    public CheckProductInCartModel(int data, String msg, int status) {
        this.data = data;
        this.msg = msg;
        this.status = status;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

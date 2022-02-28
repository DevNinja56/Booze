package com.my.booze.models;

import com.google.gson.annotations.SerializedName;

public class OrderResponseModel {

    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private OrderDataModel data;

    public OrderResponseModel(int status, String msg, OrderDataModel data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
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

    public OrderDataModel getData() {
        return data;
    }

    public void setData(OrderDataModel data) {
        this.data = data;
    }
}

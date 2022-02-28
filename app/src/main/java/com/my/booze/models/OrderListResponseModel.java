package com.my.booze.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderListResponseModel {
    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private List<OrderDataModel> data;

    public OrderListResponseModel() {
    }

    public OrderListResponseModel(int status, String msg, List<OrderDataModel> data) {
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

    public List<OrderDataModel> getData() {
        return data;
    }

    public void setData(List<OrderDataModel> data) {
        this.data = data;
    }
}

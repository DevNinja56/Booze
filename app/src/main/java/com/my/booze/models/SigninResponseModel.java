package com.my.booze.models;

import com.google.gson.annotations.SerializedName;

public class SigninResponseModel {

    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("cart_ItemsCount")
    private String cart_ItemsCount;
    @SerializedName("data")
    private UserDataModel data;


    public SigninResponseModel(int status, String msg, String cart_ItemsCount, UserDataModel data) {
        this.status = status;
        this.msg = msg;
        this.cart_ItemsCount = cart_ItemsCount;
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

    public String getCart_ItemsCount() {
        return cart_ItemsCount;
    }

    public void setCart_ItemsCount(String cart_ItemsCount) {
        this.cart_ItemsCount = cart_ItemsCount;
    }

    public UserDataModel getData() {
        return data;
    }

    public void setData(UserDataModel data) {
        this.data = data;
    }
}

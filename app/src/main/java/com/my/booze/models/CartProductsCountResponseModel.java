package com.my.booze.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartProductsCountResponseModel {
    @SerializedName("cart_ProductsCount")
    private int cart_ProductsCount;
    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private int status;

    public CartProductsCountResponseModel(int cart_ProductsCount, String msg, int status) {
        this.cart_ProductsCount = cart_ProductsCount;
        this.msg = msg;
        this.status = status;
    }

    public int getCart_ProductsCount() {
        return cart_ProductsCount;
    }

    public void setCart_ProductsCount(int cart_ProductsCount) {
        this.cart_ProductsCount = cart_ProductsCount;
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

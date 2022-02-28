package com.my.booze.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsResponseModel {

    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private List<ProductDataModel> data;

    public ProductsResponseModel() {
    }

    public ProductsResponseModel(int status, String msg, List<ProductDataModel> data) {
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

    public List<ProductDataModel> getData() {
        return data;
    }

    public void setData(List<ProductDataModel> data) {
        this.data = data;
    }
}

package com.my.booze.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartDataModel {
    @SerializedName("order_Id")
    private int order_Id;
    @SerializedName("order_SubTotal")
    private String order_SubTotal;
    @SerializedName("order_DeliveryCost")
    private String order_DeliveryCost;
    @SerializedName("order_TotalCost")
    private String order_TotalCost;
    @SerializedName("order_RemainingMinimum")
    private String order_RemainingMinimum;
    @SerializedName("order_AddressLatitude")
    private String order_AddressLatitude;
    @SerializedName("order_AddressLongitude")
    private String order_AddressLongitude;
    @SerializedName("order_CartItems")
    private List<CartProductModel> order_CartItems;
    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private int status;


    public CartDataModel() {
    }

    public CartDataModel(int order_Id, String order_SubTotal, String order_DeliveryCost, String order_TotalCost, String order_RemainingMinimum,
                         String order_AddressLatitude, String order_AddressLongitude, List<CartProductModel> order_CartItems, String msg, int status) {
        this.order_Id = order_Id;
        this.order_SubTotal = order_SubTotal;
        this.order_DeliveryCost = order_DeliveryCost;
        this.order_TotalCost = order_TotalCost;
        this.order_RemainingMinimum = order_RemainingMinimum;
        this.order_AddressLatitude = order_AddressLatitude;
        this.order_AddressLongitude = order_AddressLongitude;
        this.order_CartItems = order_CartItems;
        this.msg = msg;
        this.status = status;
    }

    public int getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(int order_Id) {
        this.order_Id = order_Id;
    }

    public String getOrder_SubTotal() {
        return order_SubTotal;
    }

    public void setOrder_SubTotal(String order_SubTotal) {
        this.order_SubTotal = order_SubTotal;
    }

    public String getOrder_DeliveryCost() {
        return order_DeliveryCost;
    }

    public void setOrder_DeliveryCost(String order_DeliveryCost) {
        this.order_DeliveryCost = order_DeliveryCost;
    }

    public String getOrder_TotalCost() {
        return order_TotalCost;
    }

    public void setOrder_TotalCost(String order_TotalCost) {
        this.order_TotalCost = order_TotalCost;
    }

    public String getOrder_RemainingMinimum() {
        return order_RemainingMinimum;
    }

    public void setOrder_RemainingMinimum(String order_RemainingMinimum) {
        this.order_RemainingMinimum = order_RemainingMinimum;
    }

    public String getOrder_AddressLatitude() {
        return order_AddressLatitude;
    }

    public void setOrder_AddressLatitude(String order_AddressLatitude) {
        this.order_AddressLatitude = order_AddressLatitude;
    }

    public String getOrder_AddressLongitude() {
        return order_AddressLongitude;
    }

    public void setOrder_AddressLongitude(String order_AddressLongitude) {
        this.order_AddressLongitude = order_AddressLongitude;
    }

    public List<CartProductModel> getOrder_CartItems() {
        return order_CartItems;
    }

    public void setOrder_CartItems(List<CartProductModel> order_CartItems) {
        this.order_CartItems = order_CartItems;
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

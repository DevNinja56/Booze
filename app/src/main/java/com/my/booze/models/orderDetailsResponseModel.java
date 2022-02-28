package com.my.booze.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class orderDetailsResponseModel {
    @SerializedName("order_Id")
    private String order_Id;
    @SerializedName("order_SubTotal")
    private String order_SubTotal;
    @SerializedName("order_DeliveryCost")
    private String order_DeliveryCost;
    @SerializedName("order_TotalCost")
    private String order_TotalCost;
    @SerializedName("order_EstimateDeliveryTime")
    private String order_EstimateDeliveryTime;
    @SerializedName("order_CartItems")
    private List<CartProductModel> order_CartItems;
    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private int status;

    public orderDetailsResponseModel() {
    }

    public orderDetailsResponseModel(String order_Id, String order_SubTotal, String order_DeliveryCost,
                                     String order_TotalCost, String order_EstimateDeliveryTime, List<CartProductModel>
                                             order_CartItems, String msg, int status) {
        this.order_Id = order_Id;
        this.order_SubTotal = order_SubTotal;
        this.order_DeliveryCost = order_DeliveryCost;
        this.order_TotalCost = order_TotalCost;
        this.order_EstimateDeliveryTime = order_EstimateDeliveryTime;
        this.order_CartItems = order_CartItems;
        this.msg = msg;
        this.status = status;
    }


    public String getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(String order_Id) {
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

    public String getOrder_EstimateDeliveryTime() {
        return order_EstimateDeliveryTime;
    }

    public void setOrder_EstimateDeliveryTime(String order_EstimateDeliveryTime) {
        this.order_EstimateDeliveryTime = order_EstimateDeliveryTime;
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

package com.my.booze.models;

import com.google.gson.annotations.SerializedName;

public class OrderDataModel {

    @SerializedName("order_Id")
    private int order_Id;
    @SerializedName("order_UserId")
    private String order_UserId;
    @SerializedName("order_ProductsIdAndQuantity")
    private String order_ProductsIdAndQuantity;
    @SerializedName("order_TotalProducts")
    private int order_TotalProducts;
    @SerializedName("order_SubTotal")
    private int order_SubTotal;
    @SerializedName("order_DeliveryCost")
    private int order_DeliveryCost;
    @SerializedName("order_Status")
    private String order_Status;
    @SerializedName("order_PlacingTime")
    private String order_PlacingTime;
    @SerializedName("'order_EstimateDeliveryTime'")
    private String order_EstimateDeliveryTime;
    @SerializedName("order_DeliveryTime")
    private String order_DeliveryTime;
    @SerializedName("order_PaymentMethod")
    private String order_PaymentMethod;
    @SerializedName("'order_RemainingMinimum'")
    private String order_RemainingMinimum;
    @SerializedName("order_AddressLatLng")
    private String order_AddressLatLng;
    @SerializedName("order_Address")
    private String order_Address;

    public OrderDataModel(int order_Id, String order_UserId, String order_ProductsIdAndQuantity, int order_TotalProducts, int order_SubTotal,
                          int order_DeliveryCost, String order_Status, String order_PlacingTime, String order_EstimateDeliveryTime,
                          String order_DeliveryTime, String order_PaymentMethod, String order_RemainingMinimum, String order_AddressLatLng, String order_Address) {
        this.order_Id = order_Id;
        this.order_UserId = order_UserId;
        this.order_ProductsIdAndQuantity = order_ProductsIdAndQuantity;
        this.order_TotalProducts = order_TotalProducts;
        this.order_SubTotal = order_SubTotal;
        this.order_DeliveryCost = order_DeliveryCost;
        this.order_Status = order_Status;
        this.order_PlacingTime = order_PlacingTime;
        this.order_EstimateDeliveryTime = order_EstimateDeliveryTime;
        this.order_DeliveryTime = order_DeliveryTime;
        this.order_PaymentMethod = order_PaymentMethod;
        this.order_RemainingMinimum = order_RemainingMinimum;
        this.order_AddressLatLng = order_AddressLatLng;
        this.order_Address = order_Address;
    }

    public int getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(int order_Id) {
        this.order_Id = order_Id;
    }

    public String getOrder_UserId() {
        return order_UserId;
    }

    public void setOrder_UserId(String order_UserId) {
        this.order_UserId = order_UserId;
    }

    public String getOrder_ProductsIdAndQuantity() {
        return order_ProductsIdAndQuantity;
    }

    public void setOrder_ProductsIdAndQuantity(String order_ProductsIdAndQuantity) {
        this.order_ProductsIdAndQuantity = order_ProductsIdAndQuantity;
    }

    public int getOrder_TotalProducts() {
        return order_TotalProducts;
    }

    public void setOrder_TotalProducts(int order_TotalProducts) {
        this.order_TotalProducts = order_TotalProducts;
    }

    public int getOrder_SubTotal() {
        return order_SubTotal;
    }

    public void setOrder_SubTotal(int order_SubTotal) {
        this.order_SubTotal = order_SubTotal;
    }

    public int getOrder_DeliveryCost() {
        return order_DeliveryCost;
    }

    public void setOrder_DeliveryCost(int order_DeliveryCost) {
        this.order_DeliveryCost = order_DeliveryCost;
    }

    public String getOrder_Status() {
        return order_Status;
    }

    public void setOrder_Status(String order_Status) {
        this.order_Status = order_Status;
    }

    public String getOrder_PlacingTime() {
        return order_PlacingTime;
    }

    public void setOrder_PlacingTime(String order_PlacingTime) {
        this.order_PlacingTime = order_PlacingTime;
    }

    public String getOrder_EstimateDeliveryTime() {
        return order_EstimateDeliveryTime;
    }

    public void setOrder_EstimateDeliveryTime(String order_EstimateDeliveryTime) {
        this.order_EstimateDeliveryTime = order_EstimateDeliveryTime;
    }

    public String getOrder_DeliveryTime() {
        return order_DeliveryTime;
    }

    public void setOrder_DeliveryTime(String order_DeliveryTime) {
        this.order_DeliveryTime = order_DeliveryTime;
    }

    public String getOrder_PaymentMethod() {
        return order_PaymentMethod;
    }

    public void setOrder_PaymentMethod(String order_PaymentMethod) {
        this.order_PaymentMethod = order_PaymentMethod;
    }

    public String getOrder_RemainingMinimum() {
        return order_RemainingMinimum;
    }

    public void setOrder_RemainingMinimum(String order_RemainingMinimum) {
        this.order_RemainingMinimum = order_RemainingMinimum;
    }

    public String getOrder_AddressLatLng() {
        return order_AddressLatLng;
    }

    public void setOrder_AddressLatLng(String order_AddressLatLng) {
        this.order_AddressLatLng = order_AddressLatLng;
    }

    public String getOrder_Address() {
        return order_Address;
    }

    public void setOrder_Address(String order_Address) {
        this.order_Address = order_Address;
    }
}

package com.my.booze.models;

import com.google.gson.annotations.SerializedName;

public class CartProductModel {

    @SerializedName("product_Id")
    private int product_Id;
    @SerializedName("product_Picture")
    private String product_Picture;
    @SerializedName("product_Name")
    private String product_Name;
    @SerializedName("product_Price")
    private String product_Price;
    @SerializedName("product_Quantity")
    private String product_Quantity;

    public CartProductModel(int product_Id, String product_Picture, String product_Name, String product_Price, String product_Quantity) {
        this.product_Id = product_Id;
        this.product_Picture = product_Picture;
        this.product_Name = product_Name;
        this.product_Price = product_Price;
        this.product_Quantity = product_Quantity;
    }

    public int getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(int product_Id) {
        this.product_Id = product_Id;
    }

    public String getproduct_Picture() {
        return product_Picture;
    }

    public void setproduct_Picture(String product_Picture) {
        this.product_Picture = product_Picture;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public String getProduct_Price() {
        return product_Price;
    }

    public void setProduct_Price(String product_Price) {
        this.product_Price = product_Price;
    }

    public String getProduct_Quantity() {
        return product_Quantity;
    }

    public void setProduct_Quantity(String product_Quantity) {
        this.product_Quantity = product_Quantity;
    }
}

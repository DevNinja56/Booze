package com.my.booze.models;

import com.google.gson.annotations.SerializedName;

public class ProductDataModel {

    @SerializedName("product_Id")
    private int product_Id;
    @SerializedName("product_Name")
    private String product_Name;
    @SerializedName("product_Varietal")
    private String product_Varietal;
    @SerializedName("product_Category")
    private String product_Category;
    @SerializedName("product_SubCategory")
    private String product_SubCategory;
    @SerializedName("product_Description")
    private String product_Description;
    @SerializedName("product_Price")
    private String product_Price;
    @SerializedName("'product_Quantity'")
    private String product_Quantity;
    @SerializedName("product_Picture")
    private String product_Picture;


    public ProductDataModel(int product_Id, String product_Name, String product_Varietal, String product_Category,
                            String product_SubCategory, String product_Description, String product_Price,
                            String product_Quantity, String product_Picture) {
        this.product_Id = product_Id;
        this.product_Name = product_Name;
        this.product_Varietal = product_Varietal;
        this.product_Category = product_Category;
        this.product_SubCategory = product_SubCategory;
        this.product_Description = product_Description;
        this.product_Price = product_Price;
        this.product_Quantity = product_Quantity;
        this.product_Picture = product_Picture;
    }

    public int getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(int product_Id) {
        this.product_Id = product_Id;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public String getProduct_Varietal() {
        return product_Varietal;
    }

    public void setProduct_Varietal(String product_Varietal) {
        this.product_Varietal = product_Varietal;
    }

    public String getProduct_SubCategory() {
        return product_SubCategory;
    }

    public void setProduct_SubCategory(String product_SubCategory) {
        this.product_SubCategory = product_SubCategory;
    }

    public String getProduct_Description() {
        return product_Description;
    }

    public void setProduct_Description(String product_Description) {
        this.product_Description = product_Description;
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

    public String getProduct_Picture() {
        return product_Picture;
    }

    public void setProduct_Picture(String product_Picture) {
        this.product_Picture = product_Picture;
    }

    public String getProduct_Category() {
        return product_Category;
    }

    public void setProduct_Category(String product_Category) {
        this.product_Category = product_Category;
    }
}

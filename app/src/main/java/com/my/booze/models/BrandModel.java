package com.my.booze.models;

public class BrandModel {
    private int id, brandImage;
    private String brandName;

    public BrandModel(int id, int brandImage, String brandName) {
        this.id = id;
        this.brandImage = brandImage;
        this.brandName = brandName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrandImage() {
        return brandImage;
    }

    public void setBrandImage(int brandImage) {
        this.brandImage = brandImage;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}

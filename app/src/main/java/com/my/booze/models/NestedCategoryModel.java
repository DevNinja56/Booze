package com.my.booze.models;

public class NestedCategoryModel {
    int id, nestedCategoryImage;
    String categoryName;
    String nestedCategoryName;

    public NestedCategoryModel(int id, int nestedCategoryImage, String categoryName, String nestedCategoryName) {
        this.id = id;
        this.nestedCategoryImage = nestedCategoryImage;
        this.categoryName = categoryName;
        this.nestedCategoryName = nestedCategoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNestedCategoryImage() {
        return nestedCategoryImage;
    }

    public void setNestedCategoryImage(int nestedCategoryImage) {
        this.nestedCategoryImage = nestedCategoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getNestedCategoryName() {
        return nestedCategoryName;
    }

    public void setNestedCategoryName(String nestedCategoryName) {
        this.nestedCategoryName = nestedCategoryName;
    }
}


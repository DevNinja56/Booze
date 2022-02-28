package com.my.booze.models;

public class CategoryModel {
    int id, categoryImage;
    String categoryName;

    public CategoryModel(int id, int categoryImage, String categoryName) {
        this.id = id;
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

package com.example.musicmg.Modals;

public class CategoryModal {
    String categoryImg,categoryName,categoryId;
   String categoryLongName;
    public CategoryModal() {
    }

    public CategoryModal(String categoryImg, String categoryName, String categoryId,String categoryLongName) {
        this.categoryImg = categoryImg;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.categoryLongName = categoryLongName;
    }

    public String getCategoryLongName() {
        return categoryLongName;
    }

    public void setCategoryLongName(String categoryLongName) {
        this.categoryLongName = categoryLongName;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}

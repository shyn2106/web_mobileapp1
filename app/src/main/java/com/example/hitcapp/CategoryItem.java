package com.example.hitcapp;
public class CategoryItem {
    private String title;
    private int imageRes;

    public CategoryItem(String title, int imageRes) {
        this.title = title;
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public int getImageRes() {
        return imageRes;
    }
}

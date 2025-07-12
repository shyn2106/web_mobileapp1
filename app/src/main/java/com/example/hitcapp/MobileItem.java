package com.example.hitcapp;

import java.io.Serializable;

public class MobileItem implements Serializable {

    private String name;
    private String description;
    private int imageResId;
    private String extraInfo;
    private String category;
    private String giaTien;

    private boolean inCart = false;

    public MobileItem(String name, String description, int imageResId, String extraInfo, String category, String giaTien) {
        this.name = name;
        this.description = description;
        this.imageResId = imageResId;
        this.extraInfo = extraInfo;
        this.category = category;
        this.giaTien = giaTien;
    }

    public MobileItem(String name, String description, int imageResId, String extraInfo, String category) {
        this(name, description, imageResId, extraInfo, category, "0₫");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public String getCategory() {
        return category;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    // ✅ THÊM PHƯƠNG THỨC NÀY
    public int getGiaTienAsInt() {
        try {
            String cleaned = giaTien.replace("₫", "")
                    .replace(".", "")
                    .replace(",", "")
                    .trim();
            return Integer.parseInt(cleaned);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

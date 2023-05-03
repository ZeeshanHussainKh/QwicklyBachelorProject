package com.example.zeeshanhussain.qwicklyapppart1;

public class ShoeData {
private String Price;
private String Category;
private String Material;
private String ProductName;
private int thumbnail;

    public ShoeData() {
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ShoeData(String price, String category, String material, String productName, int thumbnail) {
        Price = price;
        Category = category;
        Material = material;
        ProductName = productName;
        this.thumbnail = thumbnail;
    }
}

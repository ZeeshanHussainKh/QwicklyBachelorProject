package com.example.zeeshanhussain.qwicklyapppart1;

public class Product {
private  String BarcodeId;
private String Category;
private String Material;
private String Price;
private String ProductId;
private  String ProductName;
private String image_url;

    public Product() {
    }


    public Product(String barcodeId, String category, String material, String price, String productId, String productName, String image_url) {
        BarcodeId = barcodeId;
        Category = category;
        Material = material;
        Price = price;
        ProductId = productId;
        ProductName = productName;
        this.image_url = image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getBarcodeId() {
        return BarcodeId;
    }

    public void setBarcodeId(String barcodeId) {
        BarcodeId = barcodeId;
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }


}

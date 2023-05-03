package com.example.zeeshanhussain.qwicklyapppart1.Functionality.barcodescan;

public class ShoeData {
String barcode;
String category;
String material;
String ProductID;
String ProductName;
String price;
int thumbnail;

    public void setPrice(String price) {
        this.price = price;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getCategory() {
        return category;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getMaterial() {
        return material;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getProductID() {
        return ProductID;
    }

    public String getPrice() {
        return price;

    }

    public ShoeData(String barcode, String category, String material, String productID, String productName) {
        this.barcode = barcode;
        this.category = category;
        this.material = material;
        ProductID = productID;
        ProductName = productName;
    }

    public ShoeData(String price,String productID,String category,int thumbnail)
    {
        this.price=price;
        this.ProductID=productID;
        this.category=category;
        this.thumbnail=thumbnail;

    }
    public ShoeData() {
    }
}

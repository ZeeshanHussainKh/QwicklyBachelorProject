package com.example.zeeshanhussain.qwicklyapppart1.ARreco;

public class Shoes {

    private String barcodeID;
    private String category;
    private String material;

    public Shoes(){

    }

    public String getBarcodeID() {
        return barcodeID;
    }

    public String getMaterial() {
        return material;
    }

    public String getCategory() {
        return category;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBarcodeID(String barcodeID) {
        this.barcodeID = barcodeID;
    }
}

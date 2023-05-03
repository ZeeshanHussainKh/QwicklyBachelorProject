package com.example.zeeshanhussain.qwicklyapppart1.FavoritesFeature;

public class Upload {

    private String mName;
    private String mImageUrl;

    public Upload() {

    }

    public Upload(String name, String ImageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";

        }
        mName = name;
        mImageUrl = ImageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        mImageUrl=ImageUrl;
    }
}


package com.example.mycaone;

public class Image {
    private String ImgName;
    private String ImgUrl;

    public Image(String imgName, String imgUrl) {
        ImgName = imgName;
        ImgUrl = imgUrl;
    }

    public Image() {
    }

    public String getImgName() {
        return ImgName;
    }

    public void setImgName(String imgName) {
        ImgName = imgName;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
}

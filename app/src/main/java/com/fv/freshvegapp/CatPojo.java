package com.fv.freshvegapp;

public class CatPojo {
    String CatName;
    String CatImg;

    public CatPojo() {
    }

    public CatPojo(String catName, String catImg) {
        CatName = catName;
        CatImg = catImg;
    }

    public String getCatName() {
        return CatName;
    }

    public void setCatName(String catName) {
        CatName = catName;
    }

    public String getCatImg() {
        return CatImg;
    }

    public void setCatImg(String catImg) {
        CatImg = catImg;
    }
}

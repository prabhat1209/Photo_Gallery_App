package com.example.photogallery.pojo;

import android.graphics.Bitmap;

public class Picture {

    String imageName, type, is_selected;
    Bitmap image;

    public Picture(){ }

    public Picture(String imageName,String type, Bitmap image) {
        this.imageName = imageName;
        this.type = type;
        this.image = image;
    }

    public String getIs_selected() {
        return is_selected;
    }

    public void setIs_selected(String is_selected) {
        this.is_selected = is_selected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}

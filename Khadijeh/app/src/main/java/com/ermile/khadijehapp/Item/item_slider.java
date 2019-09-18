package com.ermile.khadijehapp.Item;

public class item_slider {

    String image,title,desc,url;

    public item_slider(String image, String title, String desc, String url) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

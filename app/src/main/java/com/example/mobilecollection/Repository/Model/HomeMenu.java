package com.example.mobilecollection.Repository.Model;

import java.util.List;

public class HomeMenu {
    private String title;
    private int img;
    private String desc;

    public HomeMenu() {
    }

    public HomeMenu(String title, int img, String desc) {
        this.title = title;
        this.img = img;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

package com.comp2100.todolist;

import android.widget.ImageView;

public class collection {

    private String title;
    private int imageView;


    public collection(String personal, int ic_person_black_24dp){

    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public int getImageView() {
        return imageView;
    }

    public collection(int imageView) {
        this.imageView = imageView;
    }

    public String getTitle() {
        return title;
    }

    public collection(String title) {
        this.title = title;
    }

    public void setTitle(String title, int imageView) {
        this.title = title;
        this.imageView = imageView;
    }
}





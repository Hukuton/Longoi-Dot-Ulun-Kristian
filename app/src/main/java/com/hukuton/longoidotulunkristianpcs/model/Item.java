package com.hukuton.longoidotulunkristianpcs.model;

/**
 * Created by Alixson on 01-Jul-16.
 */
public class Item {
    private String title;
    private boolean favourite;
    private int position;

    public Item(String title, boolean isFavourite){
        this.title = title;
        this.favourite = isFavourite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public int getPosition() {
        return position;
    }
}

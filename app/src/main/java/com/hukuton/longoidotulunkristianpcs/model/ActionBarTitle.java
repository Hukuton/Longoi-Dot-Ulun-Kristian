package com.hukuton.longoidotulunkristianpcs.model;

/**
 * Created by Alixson on 01-Jul-16.
 */
public class ActionBarTitle {

    private String title, subtitle;

    public ActionBarTitle(String title, String subtitle){
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}

package com.studentproject.journalapp;

import android.widget.ImageView;

public class journalshow {
    private String historyday;
    private String time;
    private String date;
    private String color;
    private ImageView viewicon;


    public journalshow(String historyday, String time, String date, String color) {
        this.historyday = historyday;
        this.time = time;
        this.date = date;
        this.color = color;

    }
    public journalshow(){}

    public String getHistoryday() {
        return historyday;
    }

    public void setHistoryday(String historyday) {
        this.historyday = historyday;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ImageView getViewicon() {
        return viewicon;
    }

    public void setViewicon(ImageView viewicon) {
        this.viewicon = viewicon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

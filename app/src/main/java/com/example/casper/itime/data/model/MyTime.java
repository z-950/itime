package com.example.casper.itime.data.model;

import java.io.Serializable;

public class MyTime implements Serializable {
    private static final long serialVersionUID = -5432234235462L;

    public String title, remark;
    public RepeatDay repeatDay;
    public Date date;
    public String imageUriPath;

    public MyTime() {
        this.repeatDay = new RepeatDay();
        this.date = new Date();
        this.imageUriPath = "";
    }

    public void setDate(int year, int month, int day) {
        this.date.year = year;
        this.date.month = month;
        this.date.day = day;
    }
}

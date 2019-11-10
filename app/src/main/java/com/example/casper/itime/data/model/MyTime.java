package com.example.casper.itime.data.model;

import java.io.Serializable;

public class MyTime implements Serializable {
    private static final long serialVersionUID = -5432234235462L;

    public String title, remark;
    public RepeatDay repeatDay;
    public Date date;

    public MyTime(String title, String remark, RepeatDay repeatDay, Date date) {
        this.title = title;
        this.remark = remark;
        this.repeatDay = repeatDay;
        this.date = date;
    }

    public MyTime() {
        this.repeatDay = new RepeatDay();
        this.date = new Date();
    }

    public void setDate(int year, int month, int day) {
        this.date.year = year;
        this.date.month = month;
        this.date.day = day;
    }
}

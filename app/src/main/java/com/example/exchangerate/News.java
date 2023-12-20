package com.example.exchangerate;


import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class News {
    private String title;
    private String details,cur;

    Double rate;
    public String getTitle() { return title; }
    public String getDetails() { return details; }

    public String getCur() { return cur; }

    public Double getRate() { return rate; }


    public News(String _title , String description ,String cur, Double rate) {
        title = _title;
        details = description;
        this.cur = cur;
        this.rate = rate;
    }
    @Override
    public String toString() {

        return title;
    }
}

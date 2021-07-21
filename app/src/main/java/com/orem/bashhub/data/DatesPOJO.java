package com.orem.bashhub.data;

import java.util.Date;

public class DatesPOJO {

    private String date, day;
    private Date time;

    public DatesPOJO(String date, String day, Date time) {
        this.date = date;
        this.day = day;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Date getDateObject() {
        return time;
    }
}

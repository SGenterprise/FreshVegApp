package com.fv.freshvegapp.Cart;

public class CalenderModel {
    String CalenderDay;
    String CalenderDate;
    String Fulldate;
    String time;

    public CalenderModel(String day, String date, String fullDate, String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CalenderModel(String calenderDay, String calenderDate, String fulldate, int mHour) {
        CalenderDay = calenderDay;
        CalenderDate = calenderDate;
        Fulldate = fulldate;
    }

    public String getCalenderDay() {
        return CalenderDay;
    }

    public void setCalenderDay(String calenderDay) {
        CalenderDay = calenderDay;
    }

    public String getCalenderDate() {
        return CalenderDate;
    }

    public void setCalenderDate(String calenderDate) {
        CalenderDate = calenderDate;
    }

    public String getFulldate() {
        return Fulldate;
    }

    public void setFulldate(String fulldate) {
        Fulldate = fulldate;
    }
}

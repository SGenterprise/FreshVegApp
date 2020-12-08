package com.fv.freshvegapp.Cart;

public class CalenderModel {
    String CalenderDay;
    String CalenderDate;
    String Fulldate;

    public CalenderModel(String calenderDay, String calenderDate, String fulldate) {
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

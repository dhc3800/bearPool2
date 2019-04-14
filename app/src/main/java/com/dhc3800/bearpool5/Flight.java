package com.dhc3800.bearpool5;

public class Flight {

    public String flightNum;
    public String from;
    public String to;
    public String leavingSpot;
    public int day;
    public int month;
    public int year;
    public int hour;
    public int minute;
    public long dateTime;
    public String uid;

    public Flight() {}

    public Flight(String flightNum, String from, String to, String leavingSpot, int day, int month, int year, int hour, int minute, String uid) {
        this.flightNum = flightNum;
        this.from = from;
        this.to = to;
        this.leavingSpot = leavingSpot;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.uid = uid;
        this.dateTime =  Utils.parseCalendarValues(this.day, this.month, this.year, this.hour, this.minute);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getLeavingSpot() {
        return leavingSpot;
    }
}

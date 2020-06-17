package com.neotys.tricentis.MongoDB.aggregate;

import org.springframework.data.mongodb.core.index.Indexed;

public class TcodeUsage {
    @Indexed
    int hour;
    @Indexed
    int minute;

    @Indexed
    String tcode;
    int numberOfCalls;
    double ratio;
    @Indexed
    long startdatems;

    int day;
    int month;
    int year;

    public TcodeUsage(int hour, int minute, String tcode, int numberOfCalls,long startdatems,int day,int month,int year) {
        this.hour = hour;
        this.minute = minute;
        this.startdatems=startdatems;
        this.tcode = tcode;
        this.numberOfCalls = numberOfCalls;
        this.day=day;
        this.month=month;
        this.year=year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getStartdatems() {
        return startdatems;
    }

    public void setStartdatems(long startdatems) {
        this.startdatems = startdatems;
    }

    public void defineRatio(int total)
    {
        if(total>0)
            this.ratio=(numberOfCalls*1.0d/total)*100;
        else
            this.ratio=0;
    }
    public double getRatio()
    {
        return this.ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }

    public int getNumberOfCalls() {
        return numberOfCalls;
    }

    public void setNumberOfCalls(int numberOfCalls) {
        this.numberOfCalls = numberOfCalls;
    }
}

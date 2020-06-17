package com.neotys.tricentis.MongoDB.aggregate;

import java.util.List;

public class TcodePerMin {

    String tcode;
    List<Usage> lisofUsageList;
    int day;
    int month;
    int year;

    public TcodePerMin(String tcode, List<Usage> lisofUsageList, int day, int month, int year) {
        this.tcode = tcode;
        this.lisofUsageList = lisofUsageList;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }

    public List<Usage> getLisofUsageList() {
        return lisofUsageList;
    }

    public void setLisofUsageList(List<Usage> lisofUsageList) {
        this.lisofUsageList = lisofUsageList;
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
}

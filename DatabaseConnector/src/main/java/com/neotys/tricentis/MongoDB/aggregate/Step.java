package com.neotys.tricentis.MongoDB.aggregate;

public class Step {
    int hour;
    int minute;
    int second;
    int month;
    int day;
    int year;
    long responseTime;
    long cputime;
    long usedBytes;
    String dynpron;

    public Step(int hour, int minute, int second, int month, int day, int year, long responseTime, long cputime, long usedBytes, String dynpron) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.month = month;
        this.day = day;
        this.year = year;
        this.responseTime = responseTime;
        this.cputime = cputime;
        this.usedBytes = usedBytes;
        this.dynpron = dynpron;
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

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public long getCputime() {
        return cputime;
    }

    public void setCputime(long cputime) {
        this.cputime = cputime;
    }

    public long getUsedBytes() {
        return usedBytes;
    }

    public void setUsedBytes(long usedBytes) {
        this.usedBytes = usedBytes;
    }

    public String getDynpron() {
        return dynpron;
    }

    public void setDynpron(String dynpron) {
        this.dynpron = dynpron;
    }
}

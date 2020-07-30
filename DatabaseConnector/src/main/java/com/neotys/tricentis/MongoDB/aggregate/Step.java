package com.neotys.tricentis.MongoDB.aggregate;

import com.neotys.tricentis.MongoDB.data.SAPStep;

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
    String tcode;
    long datesession;
    long thinktime;
    long index;

    public Step(long index,String tcode,int hour, int minute, int second, int month, int day, int year, long responseTime, long cputime, long usedBytes, String dynpron,long datesession) {
        this.hour = hour;
        this.index=index;
        this.tcode=tcode;
        this.minute = minute;
        this.second = second;
        this.month = month;
        this.day = day;
        this.year = year;
        this.responseTime = responseTime;
        this.cputime = cputime;
        this.usedBytes = usedBytes;
        this.dynpron = dynpron;
        this.datesession=datesession;
    }
    public SAPStep toSAPStep()
    {
         return new SAPStep(this.getTcode(),this.getHour(),this.getMinute(),this.getSecond(),this.getMonth(),this.getDay(), this.getYear(),this.getResponseTime(),this.getCputime(),this.getUsedBytes(),this.getDynpron(),this.getDatesession(),this.getThinktime());


    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getThinktime() {
        return thinktime;
    }

    public void setThinktime(long thinktime) {
        this.thinktime = thinktime;
    }

    public long getDatesession() {
        return datesession;
    }

    public void setDatesession(long datesession) {
        this.datesession = datesession;
    }

    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
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

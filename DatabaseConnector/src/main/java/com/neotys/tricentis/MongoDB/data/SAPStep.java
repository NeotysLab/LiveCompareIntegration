package com.neotys.tricentis.MongoDB.data;

import com.neotys.tricentis.MongoDB.aggregate.Step;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document(collection = "SAPStep")
public class SAPStep {

    @Indexed
    int hour;
    @Indexed
    int minute;
    int second;
    int month;
    int day;
    int year;
    long responseTime;
    long cputime;
    long usedBytes;
    long datesession;
    @Indexed
    String dynpron;
    @Indexed
    String tcode;

    long thinktime;

    public SAPStep(String tcode,int hour, int minute, int second, int month, int day, int year, long responseTime, long cputime, long usedBytes, String dynpron,long datesession,long thinktime ) {
        this.hour = hour;
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
        this.thinktime=thinktime;
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

package com.neotys.tricentis.MongoDB.aggregate;

import org.springframework.data.mongodb.core.index.Indexed;

public class Usage {
    int hour;
    int minute;
    int numberOfCalls;
    double ratio=0;

    public Usage(int hour, int minute, int numberOfCalls, double ratio) {
        this.hour = hour;
        this.minute = minute;
        this.numberOfCalls = numberOfCalls;
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

    public int getNumberOfCalls() {
        return numberOfCalls;
    }

    public void setNumberOfCalls(int numberOfCalls) {
        this.numberOfCalls = numberOfCalls;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}

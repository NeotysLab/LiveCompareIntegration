package com.neotys.tricentis.MongoDB.aggregate;

public class TcodeUsage {
    int hour;
    int minute;


    String tcode;
    int numberOfCalls;
    double ratio=0;

    public TcodeUsage(int hour, int minute, String tcode, int numberOfCalls) {
        this.hour = hour;
        this.minute = minute;

        this.tcode = tcode;
        this.numberOfCalls = numberOfCalls;
    }

    public void defineRatio(int total)
    {
        ratio=numberOfCalls/total;
    }
    public double getRatio()
    {
        return ratio;
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

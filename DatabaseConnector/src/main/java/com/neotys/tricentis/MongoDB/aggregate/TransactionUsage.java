package com.neotys.tricentis.MongoDB.aggregate;

public class TransactionUsage {
    int hour;
    int minute;
    int numberofcalls;

    public TransactionUsage(int hour, int minute, int numberofcalls) {
        this.hour = hour;
        this.minute = minute;
        this.numberofcalls = numberofcalls;
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

    public int getNumberofcalls() {
        return numberofcalls;
    }

    public void setNumberofcalls(int numberofcalls) {
        this.numberofcalls = numberofcalls;
    }
}

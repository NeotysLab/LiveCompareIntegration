package com.neotys.tricentis.MongoDB.aggregate;

public class TransactionPerHour {
    String tcode;
    int hour;
    int numberofcalls;

    public TransactionPerHour(String tcode, int hour, int numberofcalls) {
        this.tcode = tcode;
        this.hour = hour;
        this.numberofcalls = numberofcalls;
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

    public int getNumberofcalls() {
        return numberofcalls;
    }

    public void setNumberofcalls(int numberofcalls) {
        this.numberofcalls = numberofcalls;
    }
}

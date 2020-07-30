package com.neotys.tricentis.MongoDB.aggregate;

import com.neotys.tricentis.MongoDB.data.Dependency;

public class Flow {
    int index;
    Dependency to;
    String tcode;
    String dynpron;

    public Flow(int index, Dependency to, String tcode, String dynpron) {
        this.index = index;
        this.to = to;
        this.tcode = tcode;
        this.dynpron = dynpron;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Dependency getTo() {
        return to;
    }

    public void setTo(Dependency to) {
        this.to = to;
    }

    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }

    public String getDynpron() {
        return dynpron;
    }

    public void setDynpron(String dynpron) {
        this.dynpron = dynpron;
    }
}

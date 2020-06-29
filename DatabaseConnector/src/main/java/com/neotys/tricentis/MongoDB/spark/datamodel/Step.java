package com.neotys.tricentis.MongoDB.spark.datamodel;

import org.springframework.data.mongodb.core.index.Indexed;

public class Step {

    String dynpron;
    String tcode;

    public String getDynpron() {
        return dynpron;
    }

    public void setDynpron(String dynpron) {
        this.dynpron = dynpron;
    }

    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }

    public Step(String dynpron, String tcode) {
        this.dynpron = dynpron;
        this.tcode = tcode;
    }
}

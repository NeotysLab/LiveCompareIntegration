package com.neotys.tricentis.MongoDB.data;

import org.springframework.data.mongodb.core.index.Indexed;


public class Dependency {

    @Indexed
    private String tcode ;


    @Indexed
    private long startdatems;
    @Indexed
    private String dynpron;

    public Dependency(String tcode,  long startdatems, String dynpron) {
        this.tcode = tcode;
        this.startdatems = startdatems;
        this.dynpron = dynpron;
    }

    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }



    public long getStartdatems() {
        return startdatems;
    }

    public void setStartdatems(long startdatems) {
        this.startdatems = startdatems;
    }

    public String getDynpron() {
        return dynpron;
    }

    public void setDynpron(String dynpron) {
        this.dynpron = dynpron;
    }
}

package com.neotys.tricentis.MongoDB.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "NavigationStep")
public class NavigationStep {
    @Id
    private ObjectId id;

    @Indexed//(name="dateindex", expireAfterSeconds=72000)
    private Date dateindex;



    @Indexed
    private long startdatems;



    @Indexed
    private String tcode ;


    @Indexed
    private String account;
    private String dynpron;

    private int index;

    private Dependency from;

    private Dependency to;


    public NavigationStep(Date dateindex, long startdatems, String tcode,  String account, String dynpron, int index, Dependency from, Dependency to) {
        this.dateindex = dateindex;
        this.startdatems = startdatems;
        this.tcode = tcode;

        this.account = account;
        this.dynpron = dynpron;
        this.index = index;
        this.from = from;
        this.to = to;
    }

    public Date getDateindex() {
        return dateindex;
    }

    public void setDateindex(Date dateindex) {
        this.dateindex = dateindex;
    }

    public long getStartdatems() {
        return startdatems;
    }

    public void setStartdatems(long startdatems) {
        this.startdatems = startdatems;
    }

    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }



    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDynpron() {
        return dynpron;
    }

    public void setDynpron(String dynpron) {
        this.dynpron = dynpron;
    }



    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Dependency getFrom() {
        return from;
    }

    public void setFrom(Dependency from) {
        this.from = from;
    }

    public Dependency getTo() {
        return to;
    }

    public void setTo(Dependency to) {
        this.to = to;
    }
}

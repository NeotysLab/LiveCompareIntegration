package com.neotys.tricentis.MongoDB.aggregate;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import java.util.List;

public class UserSession {
    String account;
    List<Step> listofSession;
    long startdatems;

    public UserSession(String account, List<Step> listofSession,long startdatems) {
        this.account = account;
        this.listofSession = listofSession;
        this.startdatems=startdatems;
    }

    public long getStartdatems() {
        return startdatems;
    }

    public void setStartdatems(long startdatems) {
        this.startdatems = startdatems;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<Step> getListofSession() {
        return listofSession;
    }

    public void setListofSession(List<Step> listofSession) {
        this.listofSession = listofSession;
    }
}

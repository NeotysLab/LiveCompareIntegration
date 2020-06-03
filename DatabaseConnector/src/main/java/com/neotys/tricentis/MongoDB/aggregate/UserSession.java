package com.neotys.tricentis.MongoDB.aggregate;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import java.util.List;

public class UserSession {
    String account;
    List<Step> listofSession;

    public UserSession(String account, List<Step> listofSession) {
        this.account = account;
        this.listofSession = listofSession;
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

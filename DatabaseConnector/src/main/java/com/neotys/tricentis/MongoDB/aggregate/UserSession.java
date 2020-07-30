package com.neotys.tricentis.MongoDB.aggregate;

import com.neotys.tricentis.MongoDB.data.SAPStep;
import com.neotys.tricentis.MongoDB.data.UserSAPSession;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import java.util.ArrayList;
import java.util.Date;
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

    public UserSAPSession toUserSAPSession( Date dateindex)
    {

        List<SAPStep> list=new ArrayList<>();
        this.getListofSession().forEach(step -> {
            list.add(step.toSAPStep());
        });
        return new UserSAPSession(this.account,list,dateindex,this.getStartdatems());
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

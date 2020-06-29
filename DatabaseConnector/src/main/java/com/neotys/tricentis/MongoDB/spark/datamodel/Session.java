package com.neotys.tricentis.MongoDB.spark.datamodel;

import com.neotys.tricentis.MongoDB.data.UserSAPSession;

import java.util.ArrayList;
import java.util.List;

public class Session {
    String account;
    List<Step> session;

    public Session(UserSAPSession sapSession)
    {
        this.account=sapSession.getAccount();
        session=new ArrayList<>();
        sapSession.getListofSession().stream().forEach(sapStep -> {
            session.add(new Step(sapStep.getDynpron(),sapStep.getTcode()));
        });
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<Step> getSession() {
        return session;
    }

    public void setSession(List<Step> session) {
        this.session = session;
    }

    public Session(String account, List<Step> session) {
        this.account = account;
        this.session = session;
    }
}

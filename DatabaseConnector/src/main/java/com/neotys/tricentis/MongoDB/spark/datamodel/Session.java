package com.neotys.tricentis.MongoDB.spark.datamodel;

import com.neotys.tricentis.MongoDB.data.SAPStep;
import com.neotys.tricentis.MongoDB.data.UserSAPSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Session {
    List<List<String>> session;

    public Session(UserSAPSession sapSession)
    {

        session=new ArrayList<>();


        IntStream.range(0, sapSession.getListofSession().stream().filter(sapStep -> sapStep.getTcode() !=null && !sapStep.getTcode().isEmpty()).collect(Collectors.toList()).size())
                .forEach(idx ->
                {
                    SAPStep step=sapSession.getListofSession().get(idx);
                    session.add(Arrays.asList(step.getTcode()+":"+step.getDynpron()));
                });

    }

    public List<List<String>> getSession() {
        return session;
    }

    public void setSession(List<List<String>> session) {
        this.session = session;
    }

    public Session(List<List<String>> session) {

        this.session = session;
    }
}

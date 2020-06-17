package com.neotys.tricentis.MongoDB.data;

import com.neotys.tricentis.MongoDB.aggregate.Step;
import com.neotys.tricentis.MongoDB.aggregate.UserSession;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "UserSAPSession")
public class UserSAPSession {
    @Id
    private ObjectId id;

    @Indexed//(name="dateindex")
    private Date dateindex;

    @Indexed
    private long sessiondate;

    private String account;
    private List<SAPStep> listofSession;

    public UserSAPSession(String account, List<SAPStep> listofSession) {
        this.account = account;
        this.listofSession = listofSession;
        dateindex=new Date();
    }

    public UserSAPSession(UserSession session,Date dateindex)
    {
        this.dateindex=dateindex;
        this.account=session.getAccount();
        listofSession=new ArrayList<>();
        session.getListofSession().forEach(step -> {
            listofSession.add(new SAPStep(step));
        });
        this.sessiondate=session.getStartdatems();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<SAPStep> getListofSession() {
        return listofSession;
    }

    public void setListofSession(List<SAPStep> listofSession) {
        this.listofSession = listofSession;
    }


}

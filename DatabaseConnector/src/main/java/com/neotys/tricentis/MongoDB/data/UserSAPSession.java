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



    public UserSAPSession(String account, List<SAPStep> listofSession,Date dateindex,long sessiondate) {
        this.account = account;
        this.listofSession = listofSession;
        this.dateindex=dateindex;
        this.sessiondate=sessiondate;
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

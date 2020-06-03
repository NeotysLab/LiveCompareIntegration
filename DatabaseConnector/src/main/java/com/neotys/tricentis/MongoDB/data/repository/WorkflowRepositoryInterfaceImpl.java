package com.neotys.tricentis.MongoDB.data.repository;

import com.mongodb.client.result.UpdateResult;

import com.neotys.tricentis.MongoDB.data.Worflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class WorkflowRepositoryInterfaceImpl implements WorkflowRepositoryInterface {

    private static final String STADPARSER ="STADPARSER";
    private static final String TCODEPARSER ="TCODEPARSER";
    private static final String URLMATCHPARSERTYPE="USERPATHPARSER";


    private final  MongoOperations operations;

    @Autowired
    public WorkflowRepositoryInterfaceImpl(MongoOperations mongoOps) {

        this.operations = mongoOps;

    }

    public long getLogTimeFromTime(String type) {
        Query query = new Query();
        List<Worflow> worklist= operations.find(query.addCriteria(Criteria.where("type").is(type)),Worflow.class);
        if(worklist.size()>0)
        {
            return worklist.get(0).getDate();
        }
        else
            return 0;

    }
    public long updateLogTimeFromTime(String type) {
        long date=System.currentTimeMillis();
        Query query = new Query(Criteria.where("type").is(type));
        Update update = new Update();
        update.set("date",date );

        UpdateResult result=operations.updateFirst(query,update,Worflow.class);

        return date;

    }
    @Override
    public long getLogParerTime() {
        return getLogTimeFromTime(STADPARSER);

    }
    public long createParserTime(String type)
    {
        long date=System.currentTimeMillis();
        Worflow worflow= new Worflow(date,type);

        operations.save(worflow);
        return date;
    }
    @Override
    public long getNavigationParserTime() {
        return getLogTimeFromTime(TCODEPARSER);
    }

    @Override
    public long getUrlMatchParserTime() {
        return getLogTimeFromTime(URLMATCHPARSERTYPE);
    }

    @Override
    public long updateLogParerTime() {
        return updateLogTimeFromTime(STADPARSER);
    }

    @Override
    public long updateNavigationParserTime() {
        return updateLogTimeFromTime(TCODEPARSER);
    }

    @Override
    public long updateUrlMatchParserTime() {
        return updateLogTimeFromTime(URLMATCHPARSERTYPE);
    }
}

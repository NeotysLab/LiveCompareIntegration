package com.neotys.tricentis.MongoDB.data.repository;

import com.mongodb.client.result.UpdateResult;

import com.neotys.tricentis.MongoDB.data.Worflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;
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
    public long updateLogTimeFromTime(String type, Date executiondate) {

        Query query = new Query(Criteria.where("type").is(type));
        Update update = new Update();
        update.set("date",executiondate.getTime() );

        UpdateResult result=operations.updateFirst(query,update,Worflow.class);

        return executiondate.getTime();

    }
    @Override
    public long getLogParerTime() {
        return getLogTimeFromTime(STADPARSER);

    }
    public long createParserTime(String type, Date executiondate)
    {
        Worflow worflow= new Worflow(executiondate.getTime(),type);
        operations.save(worflow);
        return executiondate.getTime();
    }
    @Override
    public long getTcodeParserTime() {
        return getLogTimeFromTime(TCODEPARSER);
    }

    @Override
    public long getUrlMatchParserTime() {
        return getLogTimeFromTime(URLMATCHPARSERTYPE);
    }

    @Override
    public long updateLogParerTime(Date executiondate) {
        return updateLogTimeFromTime(STADPARSER,executiondate);
    }

    @Override
    public long updateTcodeParserTime(Date executiondate) {
        return updateLogTimeFromTime(TCODEPARSER, executiondate);
    }

    @Override
    public long updateUrlMatchParserTime(Date executiondate) {
        return updateLogTimeFromTime(URLMATCHPARSERTYPE, executiondate);
    }
}

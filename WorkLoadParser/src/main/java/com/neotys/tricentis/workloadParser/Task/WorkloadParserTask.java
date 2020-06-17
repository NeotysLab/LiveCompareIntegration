package com.neotys.tricentis.workloadParser.Task;


import com.neotys.tricentis.MongoDB.data.repository.WorkflowRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Date;

public class WorkloadParserTask {
    private final String type="TCODEPARSER";
    private final String typeurlMatch="URLMATCHPARSER";
    private long timestamp;
    private MongoOperations mongoOperation;
    private static final String LOGPARSERTYPE="STADPARSER";
    private WorkflowRepositoryCustom repository;
    private static final Logger logger =LoggerFactory.getLogger(WorkloadParserTask.class);

    public WorkloadParserTask(MongoOperations mongoOperation, WorkflowRepositoryCustom worflowrepo) {
        this.mongoOperation = mongoOperation;
        this.repository=worflowrepo;
    }

    public long getLasLogParserTime()
    {
        //return getParserTime(LOGPARSERTYPE);

        return repository.getLogParerTime();
    }


    public long getLasUrlMatcherTime()
    {
        //return getParserTime(LOGPARSERTYPE);

        return repository.getUrlMatchParserTime();
    }

    public long getLasTcodeParserTime()
    {
        //return getParserTime(LOGPARSERTYPE);

        return repository.getTcodeParserTime();
    }
    public long createUrlParserTime(Date executiondate)
    {
        return repository.createParserTime(typeurlMatch, executiondate);
    }

    public long updateUrlParserTime(Date executiondate)
    {
        return repository.updateUrlMatchParserTime(executiondate);

    }
    public long createTcodeParserTime(Date executiondate)
    {
       return repository.createParserTime(type, executiondate);
    }

    public long updateTcodeParserTime(Date executiondate)
    {
        return repository.updateTcodeParserTime(executiondate);


    }
}

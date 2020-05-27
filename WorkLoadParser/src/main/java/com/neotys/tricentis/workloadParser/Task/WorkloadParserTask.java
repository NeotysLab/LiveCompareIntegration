package com.neotys.tricentis.workloadParser.Task;


import com.neotys.tricentis.MongoDB.data.repository.WorkflowRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;

public class WorkloadParserTask {
    private final String type="NAVIGATIONPARSER";
    private final String typeurlMatch="URLMATCHPARSER";
    private long timestamp;
    private MongoOperations mongoOperation;
    private static final String LOGPARSERTYPE="LOGPARSER";
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

    public long getLasNavigationParserTime()
    {
        //return getParserTime(LOGPARSERTYPE);

        return repository.getNavigationParserTime();
    }
    public long createUrlParserTime()
    {
        return repository.createParserTime(typeurlMatch);
    }

    public long updateUrlParserTime()
    {
        return repository.updateUrlMatchParserTime();

    }
    public long createParserTime()
    {
       return repository.createParserTime(type);
    }

    public long updateParserTime()
    {
        return repository.updateNavigationParserTime();


    }
}

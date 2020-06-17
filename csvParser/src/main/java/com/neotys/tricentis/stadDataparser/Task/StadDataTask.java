package com.neotys.tricentis.stadDataparser.Task;

import com.neotys.tricentis.MongoDB.data.repository.WorkflowRepositoryCustom;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Date;


public class StadDataTask {
    private final String type="STADPARSER";
    private long timestamp;
    private MongoOperations mongoOperation;

    private WorkflowRepositoryCustom repository;


    public StadDataTask(MongoOperations mongoOperation, WorkflowRepositoryCustom repo)
    {
        this.mongoOperation=mongoOperation;
        this.repository=repo;

    }

    public long getLasParserTime()
    {

        return repository.getLogParerTime();


    }

    public long createParserTime(Date executiondate)
    {
        return repository.createParserTime(type,executiondate);
    }

    public long UpdateParserTime(Date executiondate)
    {

            return repository.updateLogParerTime(executiondate);
    }
}

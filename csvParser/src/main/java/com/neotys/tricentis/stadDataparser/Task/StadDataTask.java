package com.neotys.tricentis.stadDataparser.Task;

import com.neotys.tricentis.MongoDB.data.repository.WorkflowRepositoryCustom;
import org.springframework.data.mongodb.core.MongoOperations;


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

    public long createParserTime()
    {
        return repository.createParserTime(type);
    }

    public long UpdateParserTime()
    {

            return repository.updateLogParerTime();
    }
}

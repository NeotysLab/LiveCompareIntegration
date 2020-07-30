package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.aggregate.InteractionCountbyStep;
import com.neotys.tricentis.MongoDB.aggregate.TotalCountByStep;
import com.neotys.tricentis.MongoDB.aggregate.TransactionPerHour;
import com.neotys.tricentis.MongoDB.data.UserSAPSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class UserSAPSessionRepositoryImpl implements UserSAPSessionRepositoryInterface {


    private final MongoOperations operations;

    @Autowired
    public UserSAPSessionRepositoryImpl(MongoOperations mongoOps) {

        this.operations = mongoOps;

    }
    @Override
    public List<UserSAPSession> getUserSessionFromDate(long start, long end) {
        Criteria criteria = Criteria.where("sessiondate").gte(start).and("sessiondate").lte(end);
        Query query = new Query();
        query.addCriteria(criteria).with(Sort.by(Sort.Direction.ASC, "sessiondate"));
        List<UserSAPSession> resultlist=operations.find(query,UserSAPSession.class);

        return resultlist;
    }

    @Override
    public List<UserSAPSession> getUserSessionFromParsingDate(Date date) {
        Criteria filterStates = Criteria.where("dateindex").is(date);
        Query query=new Query();
        query.addCriteria(filterStates);

        return operations.find(query,UserSAPSession.class);

    }

    @Override
    public List<InteractionCountbyStep> getInteractionCountbyStep(Date parsingdate) {
        //db.getCollection('UserSAPSession').aggregate([
        //   { $unwind: { path: "$listofSession", includeArrayIndex: "arrayIndex" }},
        //   { $group: {
        //       _id: { arrayIndex: "$arrayIndex", tcode: "$listofSession.tcode", dynpron: "$listofSession.dynpron"},
        //       "count": { "$sum": 1 }
        //       }
        //   },
        //       {
        //           $sort: { "_id.arrayIndex":1}
        //       }
        //
        //
        //])
        MatchOperation filterStates = match(new Criteria("dateindex").is(parsingdate));
        UnwindOperation unwindOperation=unwind("listofSession","arrayIndex");
        GroupOperation groupByStateAndSumPop = group("arrayIndex","listofSession.tcode","listofSession.dynpron")
                .count().as("count");
        SortOperation sortByPopAsc = sort(Sort.Direction.ASC, "arrayIndex");
        Aggregation aggregation = newAggregation(filterStates,
                unwindOperation,groupByStateAndSumPop,sortByPopAsc);
        AggregationResults<InteractionCountbyStep> result = operations.aggregate(aggregation, UserSAPSession.class,InteractionCountbyStep.class);

        return result.getMappedResults();
    }

    @Override
    public List<TotalCountByStep> getTotalCountByStep(Date parsingdate) {
        MatchOperation filterStates = match(new Criteria("dateindex").is(parsingdate));
        UnwindOperation unwindOperation=unwind("listofSession","arrayIndex");
        GroupOperation groupByStateAndSumPop = group("arrayIndex")
                .count().as("count");
        SortOperation sortByPopAsc = sort(Sort.Direction.ASC, "arrayIndex");
        Aggregation aggregation = newAggregation(filterStates,
                unwindOperation,groupByStateAndSumPop,sortByPopAsc);
        AggregationResults<TotalCountByStep> result = operations.aggregate(aggregation, UserSAPSession.class,TotalCountByStep.class);

        return result.getMappedResults();
    }
}

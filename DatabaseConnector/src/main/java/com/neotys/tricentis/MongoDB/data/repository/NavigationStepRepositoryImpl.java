package com.neotys.tricentis.MongoDB.data.repository;

import com.mongodb.BasicDBObject;
import com.neotys.tricentis.MongoDB.aggregate.SessionPath;
import com.neotys.tricentis.MongoDB.aggregate.UserFlow;
import com.neotys.tricentis.MongoDB.data.NavigationStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Date;
import java.util.List;

import static com.neotys.tricentis.MongoDB.config.Constants.TYPE_TRANSACTIONS;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

public class NavigationStepRepositoryImpl implements  NavigationStepRepositoryCustom {
    private final MongoOperations operations;

    @Autowired
    public NavigationStepRepositoryImpl(MongoOperations mongoOps) {

        this.operations = mongoOps;

    }
    @Override
    public void saveNavigationsSteps(List<NavigationStep> navigationSteps) {
        navigationSteps.stream().forEach(navigationStep -> {
            operations.save(navigationStep);
        });
    }

    @Override
    public List<SessionPath> getSessionPath(Date date) {
        //db.getCollection('NavigationStep').aggregate([
        //             { $group: {
        //               _id: { arrayIndex: "$index",tcode:"$tcode",dynpron:"$dynpron"},
        //               listofactions:{ $addToSet:{ from: "$from",to: "$to"}
        //               },
        //               count: { $sum :1}
        //                }
        //            },
        //            {
        //                $sort:{ "_id.arrayIndex":1}
        //                }
        //])
        MatchOperation filterStates = match(new Criteria("dateindex").is(date));
        GroupOperation groupByStateAndSumPop = group("index","tcode","dynpron","to")
                .addToSet(new BasicDBObject("from","$from")
                        .append("to","$to")).as("listofactions")
                .count().as("count");

        SortOperation sortByPopAsc = sort(Sort.Direction.ASC, "index");


        Aggregation aggregation = newAggregation(filterStates,
                groupByStateAndSumPop, sortByPopAsc);
        AggregationResults<SessionPath> result = operations.aggregate(aggregation, NavigationStep.class,SessionPath.class);

        return result.getMappedResults();
    }

    @Override
    public List<SessionPath> getSessionPathFromDates(long start, long end) {
        MatchOperation filterStates = match(new Criteria("startdatems").lte(start).andOperator(Criteria.where("startdatems").gte(end)));
        GroupOperation groupByStateAndSumPop = group("index","tcode","dynpron","to")
                .addToSet(new BasicDBObject("from","$from")
                        .append("to","$to")).as("listofactions")
                .count().as("count");

        SortOperation sortByPopAsc = sort(Sort.Direction.ASC, "index");


        Aggregation aggregation = newAggregation(filterStates,
                groupByStateAndSumPop, sortByPopAsc);
        AggregationResults<SessionPath> result = operations.aggregate(aggregation, NavigationStep.class,SessionPath.class);

        return result.getMappedResults();
    }

    @Override
    public List<UserFlow> getUserFlow(Date date) {
        MatchOperation filterStates = match(new Criteria("dateindex").is(date));
        GroupOperation groupByStateAndSumPop = group("account")
                .first("account").as("account")
                .addToSet(new BasicDBObject("tcode","$tcode")
                        .append("dynpron","$dynpron")
                        .append("index","$index")
                        .append("to","$to")).as("flow");
        UnwindOperation unwindOperation=unwind("flow");
        SortOperation sortByPopAsc = sort(Sort.Direction.ASC, "account","flow.index");

        GroupOperation groupOperation= group("account")
                                    .push("flow").as("flow")
                                    .first("account").as("account");

        Aggregation aggregation = newAggregation(filterStates,
                groupByStateAndSumPop,unwindOperation, sortByPopAsc,groupOperation);
        AggregationResults<UserFlow> result = operations.aggregate(aggregation, NavigationStep.class,UserFlow.class);

        return result.getMappedResults();
    }


    //db.getCollection('NavigationStep').aggregate([
    //             { $group: {
    //               _id: { arrayIndex: "$index"},
    //               listofactions:{ $addToSet:{ from: "$from",to: "$to"}
    //               },
    //               count: { $sum :1}
    //                }
    //            },
    //            {
    //                $sort:{ "_id.arrayIndex":1}
    //                }
    //])
}

package com.neotys.tricentis.MongoDB.data.repository;

import com.mongodb.BasicDBObject;
import com.neotys.tricentis.MongoDB.aggregate.TcodeUsage;
import com.neotys.tricentis.MongoDB.aggregate.TransactionPerHour;
import com.neotys.tricentis.MongoDB.aggregate.TransactionUsage;
import com.neotys.tricentis.MongoDB.aggregate.UserSession;
import com.neotys.tricentis.MongoDB.data.StadData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static com.neotys.tricentis.MongoDB.config.Constants.TYPE_TRANSACTIONS;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class StadDataRepositoryImpl implements StadDataRepositoryCustom {
    private final MongoOperations operations;

    @Autowired
    public StadDataRepositoryImpl(MongoOperations mongoOps) {

        this.operations = mongoOps;

    }

    @Override
    public List<StadData> getTcodebyCreationDateLessthan(long creation_date) {
        /*Query query = new Query();
        query.addCriteria(Criteria.where("creation_date").equals(creation_date)).with(new Sort(Sort.Direction.ASC,"startdatems")).g);
        List<RequestLogData> requestLogDataList=operations.find(query,RequestLogData.class);
        return requestLogDataList.stream();*/
        return null;
    }

    @Override
    public Stream<StadData> searchCreationDateLessThanOrderByTcodeAscdateAsc(long start) {
        return null;
    }

    @Override
    public void dropCollectionStadData() {

    }

    @Override
    public List<UserSession> getUserSessionFromdate(long date) {
            MatchOperation filterStates = match(new Criteria("dateindex").is(new Date(date)).and("taskType").in(TYPE_TRANSACTIONS).and("tcode").exists(true).andOperator(Criteria.where("tcode").ne("").and("dynpron").ne("")));
            GroupOperation groupByStateAndSumPop = group("account")
                    .addToSet(new BasicDBObject("tcode","$tcode")
                            .append("responseTime","$responseTime")
                            .append("dynpron","$dynpron")
                            .append("cputime","$cputime")
                            .append("usedBytes","$usedBytes")
                            .append("hour","$hour")
                            .append("minute","$minute")
                            .append("second","$second")
                            .append("day","$day")
                            .append("month","$month")
                            .append("datesession","$startdatems")
                            .append("index","$index")
                            .append("year","$year")).as("listofSession")
                    .first("startdatems").as("startdatems")
                    .first("account").as("account");
           UnwindOperation unwindOperation=unwind("listofSession");

           SortOperation sortByPopAsc = sort(Sort.Direction.ASC, "listofSession.index","listofSession.year","listofSession.month","listofSession.day","listofSession.hour","listofSession.minute","listofSession.second");
           GroupOperation groupOperation=group("account")
                                    .push("listofSession").as("listofSession")
                                    .first("startdatems").as("startdatems")
                                    .first("account").as("account");

            Aggregation aggregation = newAggregation(filterStates,
                    groupByStateAndSumPop, unwindOperation,sortByPopAsc,groupOperation);
            AggregationResults<UserSession> result = operations.aggregate(aggregation, StadData.class,UserSession.class);

           return result.getMappedResults();
     }

    @Override
    public List<TcodeUsage> getTcodeUsagefromDate(long start) {
        MatchOperation filterStates = match(new Criteria("taskType").in(TYPE_TRANSACTIONS).and("tcode").exists(true).and("dateindex").is( new Date(start)));
        GroupOperation groupByStateAndSumPop = group("tcode","hour","minute","account")
                .first("startdatems").as("startdatems")
                .first("year").as("year")
                .first("month").as("month")
                .first("day").as("day");
        GroupOperation groupByStateAndsecond = group("tcode","hour","minute")
                .count().as("numberOfCalls")
                .first("startdatems").as("startdatems")
                .first("year").as("year")
                .first("month").as("month")
                .first("day").as("day");

        SortOperation sortByPopAsc = sort(Sort.Direction.ASC, "hour","minute");
        Aggregation aggregation = newAggregation(filterStates,
                groupByStateAndSumPop,groupByStateAndsecond,sortByPopAsc);
        AggregationResults<TcodeUsage> result = operations.aggregate(aggregation, StadData.class,TcodeUsage.class);

        return result.getMappedResults();
    }

    @Override
    public List<TcodeUsage> getTcodeUsagefromDate(long start, long end) {

        /*db.getCollection('StadData').aggregate(
   [  {
           $match:
                   { taskType:"D", tcode:{$exists :1} }
       },
       { $group:{
            _id: {tcode:"$tcode",hour:"$hour",minute:"$minute",dynpron:"$dynpron" }
           , count: {$sum:1}

        }},{
         $sort :{count : 1,tcode:1 }
     }

     ]);*/
        MatchOperation filterStates = match(new Criteria("taskType").in(TYPE_TRANSACTIONS).and("tcode").exists(true).andOperator(Criteria.where("startdatems").gt(start),Criteria.where("startdatems").lt(end)));
        GroupOperation groupByStateAndSumPop = group("tcode","hour","minute","account")
                .first("year").as("year")
                .first("month").as("month")
                .first("day").as("day");
        GroupOperation groupByStateAndsecond = group("tcode","hour","minute")
                .count().as("numberOfCalls")
                .first("startdatems").as("startdatems")
                .first("year").as("year")
                .first("month").as("month")
                .first("day").as("day");

        SortOperation sortByPopAsc = sort(Sort.Direction.ASC, "hour","minute");
        Aggregation aggregation = newAggregation(filterStates,
                groupByStateAndSumPop,groupByStateAndsecond,sortByPopAsc);
        AggregationResults<TcodeUsage> result = operations.aggregate(aggregation, StadData.class,TcodeUsage.class);

        return result.getMappedResults();
    }

    @Override
    public List<TransactionUsage> getTransactionUsageFromDate(long start, long end) {
        //db.getCollection('StadData').aggregate(
        //[ {
        //$match:
        //{ taskType:"D", tcode:{$exists :1} }
        //},
        //{ $group:{
        //_id: {tcode:"$tcode",hour:"$hour",minute:"$minute" }
        //
        //
        //}},
        //{$group:{_id:{hour: '$_id.hour',minute:"$_id.minute"},
        //              numberofcals:{$sum:1}}
        //          }
        //
        //,{
        //$sort :{"_id.hour" : 1,"_id.minute":1 }
        //}
        MatchOperation filterStates = match(new Criteria("taskType").in(TYPE_TRANSACTIONS).and("tcode").exists(true).andOperator(Criteria.where("startdatems").gt(start),Criteria.where("startdatems").lt(end)));
        GroupOperation groupByStateAndSumPop = group("tcode","hour","minute")
                .first("year").as("year")
                .first("month").as("month")
                .first("day").as("day")
                .count().as("numberofcalls");
        GroupOperation groupSecond=group("hour","minute")
                .sum("numberofcalls").as("numberofcalls")
                .first("year").as("year")
                .first("month").as("month")
                .first("day").as("day");
        SortOperation sortByPopAsc = sort(Sort.Direction.ASC, "hour","minute");
        Aggregation aggregation = newAggregation(filterStates,
                groupByStateAndSumPop,groupSecond,sortByPopAsc);
        AggregationResults<TransactionUsage> result = operations.aggregate(aggregation, StadData.class,TransactionUsage.class);

        return result.getMappedResults();
    }

    @Override
    public List<TransactionUsage> getTransactionUsageFromDate(long start) {
        MatchOperation filterStates = match(new Criteria("taskType").in(TYPE_TRANSACTIONS).and("tcode").exists(true).and("dateindex").is(new Date(start)));
        GroupOperation groupByStateAndSumPop = group("tcode","hour","minute")
                .first("year").as("year")
                .first("month").as("month")
                .first("day").as("day")
                .count().as("numberofcalls");
        GroupOperation groupSecond=group("hour","minute").sum("numberofcalls").as("numberofcalls")
                .first("year").as("year")
                .first("month").as("month")
                .first("day").as("day");
        SortOperation sortByPopAsc = sort(Sort.Direction.ASC, "hour","minute");
        Aggregation aggregation = newAggregation(filterStates,
                groupByStateAndSumPop,groupSecond,sortByPopAsc);
        AggregationResults<TransactionUsage> result = operations.aggregate(aggregation, StadData.class,TransactionUsage.class);

        return result.getMappedResults();
    }

    @Override
    public List<TransactionPerHour> getTransactionPerHourFromDate(long start, long end) {
        //db.getCollection('StadData').aggregate(
        //[ {
        //$match:
        //{ taskType:"D", tcode:{$exists :1} }
        //},
        //{ $group:{
        //_id: {tcode:"$tcode",hour:"$hour",account:"$account" }
        //}}
        //              ,
        //{$group:{_id:{tcode: '$_id.tcode'},hour:{$first:"$_id.hour"},
        //              numberofcals:{$sum:1}}
        //          }
        //,{
        //$sort :{"hour" : 1}
        //}
        //
        //]);
        MatchOperation filterStates = match(new Criteria("taskType").in(TYPE_TRANSACTIONS).and("tcode").exists(true).andOperator(Criteria.where("startdatems").gt(start),Criteria.where("startdatems").lt(end)));
        GroupOperation groupByStateAndSumPop = group("tcode","hour","account");
        GroupOperation groupSecond=group("tcode","hour").count().as("numberofcalls");
        SortOperation sortByPopAsc = sort(Sort.Direction.ASC, "hour");
        Aggregation aggregation = newAggregation(filterStates,
                groupByStateAndSumPop,groupSecond,sortByPopAsc);
        AggregationResults<TransactionPerHour> result = operations.aggregate(aggregation, StadData.class,TransactionPerHour.class);

        return result.getMappedResults();
    }

    @Override
    public List<TransactionPerHour> getTransactionPerHourFromDate(long start) {
        MatchOperation filterStates = match(new Criteria("taskType").in(TYPE_TRANSACTIONS).and("tcode").exists(true).and("dateindex").is(new Date(start)));
        GroupOperation groupByStateAndSumPop = group("tcode","hour","account");
        GroupOperation groupSecond=group("tcode","hour").count().as("numberofcalls");
        SortOperation sortByPopAsc = sort(Sort.Direction.ASC, "hour");
        Aggregation aggregation = newAggregation(filterStates,
                groupByStateAndSumPop,groupSecond,sortByPopAsc);
        AggregationResults<TransactionPerHour> result = operations.aggregate(aggregation, StadData.class,TransactionPerHour.class);

        return result.getMappedResults();
    }


}

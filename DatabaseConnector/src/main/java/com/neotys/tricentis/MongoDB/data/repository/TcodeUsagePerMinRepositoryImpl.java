package com.neotys.tricentis.MongoDB.data.repository;

import com.mongodb.BasicDBObject;
import com.neotys.tricentis.MongoDB.aggregate.TcodePerMin;
import com.neotys.tricentis.MongoDB.data.TCodeUsagePerMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class TcodeUsagePerMinRepositoryImpl implements TcodeUsagePerMinRepositoryInterface {

    private final MongoOperations operations;

    @Autowired
    public TcodeUsagePerMinRepositoryImpl(MongoOperations mongoOps) {

        this.operations = mongoOps;

    }

    @Override
    public List<TCodeUsagePerMin> getTcodeUsagefromDate(long start, long end) {
        Criteria criteria = Criteria.where("startdatems").gte(start).andOperator(Criteria.where("startdatems").lte(end));
        Query query = new Query();
        query.addCriteria(criteria).with(Sort.by(Sort.Direction.ASC, "startdatems"));
        List<TCodeUsagePerMin> resultlist=operations.find(query,TCodeUsagePerMin.class);

        return resultlist;
    }

    @Override
    public List<TcodePerMin> getTcodePerminfromDates(long start, long end) {
        //db.getCollection('TCodeUsagePerMin').aggregate([
        //
        //    {
        //        $group : { _id:{tcode:"$tcode",day:"$day",month:"$month",year:"$year"},
        //                listofusage: { $addToSet : {numberOfCalls:"$numberOfCalls",
        //                                        hour:"$hour",
        //                                        minute:"$minute",
        //                                        ratio:"$ratio"},
        //
        //
        //                            }
        //                    }
        //    },
        //
        //     {$sort: {'_id.tcode':1,'listofusage.hour': 1,'listofusage.minute':1}},
        //
        //])
        MatchOperation filterStates = match(new Criteria("startdatems").gte(start).andOperator(Criteria.where("startdatems").lte(end)));
        GroupOperation groupByStateAndSumPop = group("tcode","day","month","year")
                .addToSet(new BasicDBObject("numberOfCalls","$numberOfCalls")
                        .append("hour","$hour")
                        .append("minute","$minute")
                        .append("ratio","$ratio")
                         ).as("lisofUsageList");
        SortOperation sortByPopAsc = sort(Sort.Direction.ASC, "tcode","year","month","day","lisofUsageList.hour","lisofUsageList.minute");
        Aggregation aggregation = newAggregation(filterStates,
                groupByStateAndSumPop,sortByPopAsc);
        AggregationResults<TcodePerMin> result = operations.aggregate(aggregation, TCodeUsagePerMin.class,TcodePerMin.class);

        return result.getMappedResults();
    }
}

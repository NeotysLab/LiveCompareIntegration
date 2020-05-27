package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.data.StadData;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.stream.Stream;

public class StadDataRepositoryImpl implements StadDataRepositoryCustom {
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
}

package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.data.StadData;

import java.util.List;
import java.util.stream.Stream;

public interface StadDataRepositoryCustom {

    List<StadData> getTcodebyCreationDateLessthan(long creation_date);

    Stream<StadData> searchCreationDateLessThanOrderByTcodeAscdateAsc(long start);


    void dropCollectionStadData();
}

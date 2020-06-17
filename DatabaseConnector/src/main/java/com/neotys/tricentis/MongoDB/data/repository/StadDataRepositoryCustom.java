package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.aggregate.TcodeUsage;
import com.neotys.tricentis.MongoDB.aggregate.TransactionPerHour;
import com.neotys.tricentis.MongoDB.aggregate.TransactionUsage;
import com.neotys.tricentis.MongoDB.aggregate.UserSession;
import com.neotys.tricentis.MongoDB.data.StadData;

import java.util.List;
import java.util.stream.Stream;

public interface StadDataRepositoryCustom {

    List<StadData> getTcodebyCreationDateLessthan(long creation_date);

    Stream<StadData> searchCreationDateLessThanOrderByTcodeAscdateAsc(long start);


    void dropCollectionStadData();

    List<UserSession> getUserSessionFromdate(long date);

    List<TcodeUsage> getTcodeUsagefromDate(long start);

    List<TcodeUsage> getTcodeUsagefromDate(long start, long end);

    List<TransactionUsage> getTransactionUsageFromDate(long start, long end);
    List<TransactionUsage> getTransactionUsageFromDate(long start);

    List<TransactionPerHour> getTransactionPerHourFromDate(long start, long end);
    List<TransactionPerHour> getTransactionPerHourFromDate(long start);

}

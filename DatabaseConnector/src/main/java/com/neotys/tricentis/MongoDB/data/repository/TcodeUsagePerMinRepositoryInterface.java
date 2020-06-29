package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.aggregate.TcodePerMin;
import com.neotys.tricentis.MongoDB.data.TCodeUsagePerMin;

import java.util.List;

public interface TcodeUsagePerMinRepositoryInterface {
    List<TCodeUsagePerMin> getTcodeUsagefromDate(long start, long end);

    List<TcodePerMin>  getTcodePerminfromDates(long start,long end);


}

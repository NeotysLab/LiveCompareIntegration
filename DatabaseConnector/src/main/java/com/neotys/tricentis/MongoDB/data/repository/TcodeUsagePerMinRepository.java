package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.data.TCodeUsagePerMin;
import com.neotys.tricentis.MongoDB.data.UserSAPSession;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TcodeUsagePerMinRepository extends MongoRepository<TCodeUsagePerMin, ObjectId>, TcodeUsagePerMinRepositoryInterface
{
    /**
     * Derived query selecting by {@code creation_date}.
     *
     * @param creation_date
     * @return
     */
    @Query("{ 'sessiondate' : ?0 }")
    TCodeUsagePerMin findBycreation_dateLessThen(long creation_date);


}

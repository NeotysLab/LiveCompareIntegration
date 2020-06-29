package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.data.StadData;
import com.neotys.tricentis.MongoDB.data.UserSAPSession;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserSAPSessionRepository extends MongoRepository<UserSAPSession, ObjectId>, UserSAPSessionRepositoryInterface
{
    /**
     * Derived query selecting by {@code creation_date}.
     *
     * @param creation_date
     * @return
     */
    @Query("{ 'sessiondate' : ?0 }")
    UserSAPSession findBycreation_dateLessThen(long creation_date);




}

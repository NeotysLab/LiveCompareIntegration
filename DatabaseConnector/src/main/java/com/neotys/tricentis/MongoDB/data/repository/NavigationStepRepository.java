package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.data.NavigationStep;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface NavigationStepRepository extends MongoRepository<NavigationStep, ObjectId>, NavigationStepRepositoryCustom{
    /**
     * Derived query selecting by {@code creation_date}.
     *
     * @param creation_date
     * @return
     */
    @Query("{ 'startdatems' : ?0 }")
    NavigationStep findBycreation_dateLessThen(long creation_date);

}

package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.data.StadData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface StadDataRepository extends MongoRepository<StadData, ObjectId>, StadDataRepositoryCustom
{
/**
 * Derived query selecting by {@code creation_date}.
 *
 * @param creation_date
 * @return
 */
@Query("{ 'startdatems' : ?0 }")
   StadData findBycreation_dateLessThen(long creation_date);

}

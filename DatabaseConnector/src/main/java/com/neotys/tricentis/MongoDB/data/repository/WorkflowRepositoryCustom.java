package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.data.Worflow;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface WorkflowRepositoryCustom extends MongoRepository<Worflow, ObjectId>, WorkflowRepositoryInterface {
    /**
     * Derived query selecting by {@code creation_date}.
     *
     * @param type
     * @return
     */
    @Query("{ 'type' : ?0 }")
    Worflow findBytype(String type);
}

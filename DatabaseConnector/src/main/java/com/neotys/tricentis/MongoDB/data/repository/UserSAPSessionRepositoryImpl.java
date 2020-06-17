package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.data.UserSAPSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserSAPSessionRepositoryImpl implements UserSAPSessionRepositoryInterface {


    private final MongoOperations operations;

    @Autowired
    public UserSAPSessionRepositoryImpl(MongoOperations mongoOps) {

        this.operations = mongoOps;

    }
    @Override
    public List<UserSAPSession> getUserSessionFromDate(long start, long end) {
        Criteria criteria = Criteria.where("sessiondate").gte(start).and("sessiondate").lte(end);
        Query query = new Query();
        query.addCriteria(criteria).with(Sort.by(Sort.Direction.ASC, "sessiondate"));
        List<UserSAPSession> resultlist=operations.find(query,UserSAPSession.class);

        return resultlist;
    }
}

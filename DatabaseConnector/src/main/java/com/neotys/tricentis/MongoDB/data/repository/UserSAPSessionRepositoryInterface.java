package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.data.UserSAPSession;

import java.util.List;

public interface UserSAPSessionRepositoryInterface {

    List<UserSAPSession> getUserSessionFromDate(long start,long end);

}

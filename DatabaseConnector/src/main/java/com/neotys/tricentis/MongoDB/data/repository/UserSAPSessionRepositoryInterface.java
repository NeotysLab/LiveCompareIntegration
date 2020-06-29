package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.aggregate.InteractionCountbyStep;
import com.neotys.tricentis.MongoDB.aggregate.TotalCountByStep;
import com.neotys.tricentis.MongoDB.data.UserSAPSession;

import java.util.List;

public interface UserSAPSessionRepositoryInterface {

    List<UserSAPSession> getUserSessionFromDate(long start,long end);

    List<UserSAPSession> getUserSessionFromParsingDate(long date);

    List<InteractionCountbyStep> getInteractionCountbyStep(long parsingdate);

    List<TotalCountByStep> getTotalCountByStep(long parsingdate);

}

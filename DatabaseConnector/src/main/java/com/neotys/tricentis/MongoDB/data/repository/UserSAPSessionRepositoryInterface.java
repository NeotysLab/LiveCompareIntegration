package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.aggregate.InteractionCountbyStep;
import com.neotys.tricentis.MongoDB.aggregate.TotalCountByStep;
import com.neotys.tricentis.MongoDB.data.UserSAPSession;

import java.util.Date;
import java.util.List;

public interface UserSAPSessionRepositoryInterface {

    List<UserSAPSession> getUserSessionFromDate(long start,long end);

    List<UserSAPSession> getUserSessionFromParsingDate(Date date);

    List<InteractionCountbyStep> getInteractionCountbyStep(Date parsingdate);

    List<TotalCountByStep> getTotalCountByStep(Date parsingdate);

}

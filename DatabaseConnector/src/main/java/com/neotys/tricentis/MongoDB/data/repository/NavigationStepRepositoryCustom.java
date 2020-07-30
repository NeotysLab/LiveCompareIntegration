package com.neotys.tricentis.MongoDB.data.repository;

import com.neotys.tricentis.MongoDB.aggregate.SessionPath;
import com.neotys.tricentis.MongoDB.aggregate.UserFlow;
import com.neotys.tricentis.MongoDB.data.NavigationStep;

import java.util.Date;
import java.util.List;

public interface NavigationStepRepositoryCustom {

     void saveNavigationsSteps(List<NavigationStep> navigationSteps);

     List<SessionPath> getSessionPath(Date date);

     List<SessionPath> getSessionPathFromDates(long start,long end);

     List<UserFlow> getUserFlow(Date date);
}

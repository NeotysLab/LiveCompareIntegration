package com.neotys.tricentis.MongoDB.data.repository;

import java.util.Date;

public interface WorkflowRepositoryInterface {

        long getLogParerTime();

        long getTcodeParserTime();

        long getUrlMatchParserTime();

        long updateLogParerTime(Date executiondate);

        long updateTcodeParserTime(Date executiondate);

        long createParserTime(String type, Date executiondate);
        long updateUrlMatchParserTime(Date executiondate);

}

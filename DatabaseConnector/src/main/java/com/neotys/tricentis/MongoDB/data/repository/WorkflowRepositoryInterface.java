package com.neotys.tricentis.MongoDB.data.repository;

public interface WorkflowRepositoryInterface {

        long getLogParerTime();

        long getNavigationParserTime();

        long getUrlMatchParserTime();

        long updateLogParerTime();

        long updateNavigationParserTime();

        long createParserTime(String type);
        long updateUrlMatchParserTime();

}

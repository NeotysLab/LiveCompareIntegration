package com.neotys.tricentis.workloadParser.service;

import java.io.IOException;
import java.util.Date;

public interface WorkloadSeriviceInterface   {
    void generateUserSession();
    void   initService();

    void detectCommonPatern(Date extractordate);

    void closeService();
}

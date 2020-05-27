package com.neotys.tricentis.workloadParser.scheduler;

import com.neotys.tricentis.workloadParser.app.WorkloadParserConfig;
import com.neotys.tricentis.workloadParser.service.WorkloadPaserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WorkloadParserScheduler {
    private static final Logger logger =LoggerFactory.getLogger(WorkloadParserScheduler.class);

    @Autowired
    private WorkloadPaserService parserservice;

    @Scheduled(cron="1 * * * * *")
    public void GenerateNavigationParsing() {
        WorkloadParserConfig config=new WorkloadParserConfig();
        try {
            logger.info("task started");
           // parserservice.generateStats(config.getORDERRATIO(),config.getHITRATIO(),config.getACCEPTABLERATIO(),config.getMINNAVIGATIONSIZE(),config.getMAXMILLISECONDSBETWEENREQUESTS(),config.getACCEPTABLERATIOMID(),config.getSTATICEXPRESSION());
         //   parserservice.parseNavigation(config.getResultPath(),config.getORDERRATIO(),config.getHITRATIO(),config.getACCEPTABLERATIO(),config.getMINNAVIGATIONSIZE(),config.getMAXMILLISECONDSBETWEENREQUESTS(),config.getACCEPTABLERATIOMID(),config.getSTATICEXPRESSION());
           // parserservice.generateaggregate(config.getORDERRATIO(),config.getHITRATIO(),config.getACCEPTABLERATIO(),config.getMINNAVIGATIONSIZE(),config.getMAXMILLISECONDSBETWEENREQUESTS(),config.getACCEPTABLERATIOMID(),config.getSTATICEXPRESSION());
            logger.info("task ended");
        } catch (Exception e) {
            logger.error("error during the parsing ",e);
        }

    }
}

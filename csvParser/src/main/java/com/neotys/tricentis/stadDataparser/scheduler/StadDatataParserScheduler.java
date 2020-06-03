package com.neotys.tricentis.stadDataparser.scheduler;

import com.neotys.tricentis.MongoDB.EnvironmentException;
import com.neotys.tricentis.stadDataparser.app.StadDataConfig;
import com.neotys.tricentis.stadDataparser.exception.StadDataConfigExceptin;
import com.neotys.tricentis.stadDataparser.parser.StadDataParserImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;
import static com.neotys.tricentis.stadDataparser.Config.Constants.SECRET_DIR;

@Component
public class StadDatataParserScheduler {
    private static final Logger logger =LoggerFactory.getLogger(StadDatataParserScheduler.class);

    @Autowired
    private StadDataParserImpl logservice;
    private String testingdirectory;

    @Scheduled(cron="1 * * * * *")
    public void generateNavigationParsing() throws StadDataConfigExceptin {
        try {
            StadDataConfig config = new StadDataConfig();

            logger.info("Starting log parsing task");
            logger.info("Parsing directory "+config.getTESTINGDIRECTORY());
            logservice.setDirPath(config.getTESTINGDIRECTORY());
            logservice.scanForLogFiles();
            logger.info("Log parsing finished");
        } catch(StadDataConfigExceptin e)
        {
            logger.error("Configuration issue ", e);
        }  catch(Exception e) {
            logger.error("error during the parsing ", e);
        }
    }




}

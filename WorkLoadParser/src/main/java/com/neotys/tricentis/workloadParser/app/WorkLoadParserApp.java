package com.neotys.tricentis.workloadParser.app;

import com.neotys.tricentis.MongoDB.data.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@Configuration
@EnableScheduling
@EnableAutoConfiguration
@Import(com.neotys.tricentis.MongoDB.config.MongoConfig.class)
@EntityScan(basePackages = {"com.neotys.tricentis"})
@EnableMongoRepositories( {"com.neotys.tricentis.MongoDB.data.repository"})
@ComponentScan({"com.neotys.tricentis.MongoDB.DatabaseConnector.data.repository","com.neotys.tricentis.workloadParser.scheduler", "com.neotys.tricentis.workloadParser.service","com.neotys.tricentis.workloadParser.controller"})
public class WorkLoadParserApp implements CommandLineRunner {
    private static final Logger logger =LoggerFactory.getLogger(WorkLoadParserApp.class);

    public static void main(String[] args) {
        SpringApplication.run(WorkLoadParserApp.class, args);
    }

    @Bean
    CommandLineRunner init(WorkflowRepositoryCustom repository, StadDataRepository stadDataRepository,UserSAPSessionRepository userSAPSessionRepository,TcodeUsagePerMinRepository tcodeUsagePerMinRepository,NavigationStepRepository navigationStepRepository) {
        return args -> {
            logger.debug("Repository: " + repository);
        };
    }

    @Override
    public void run(final String... args)  {
        try {

            logger.info("Lauching application");
                 }
        catch (Exception e)
        {
            logger.error("error in main " ,e);
            e.printStackTrace();

        }
       //
    }


}

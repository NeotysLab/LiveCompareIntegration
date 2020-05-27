package com.neotys.tricentis.stadDataparser.app;


import com.neotys.tricentis.MongoDB.data.repository.WorkflowRepositoryCustom;
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
@EntityScan(basePackages = {"com.neotys.tricentis.MongoDB"})
@EnableMongoRepositories( {"com.neotys.tricentis.MongoDB.data.repository"})
@ComponentScan({"com.neotys.tricentis.MongoDB.DatabaseConnector.data.repository","com.neotys.tricentis.stadDataparser.scheduler", "com.neotys.tricentis.stadDataparser.parser"})
public class ParseLog implements CommandLineRunner {
    private static final org.slf4j.Logger logger =LoggerFactory.getLogger(ParseLog.class);

    public static void main(String[] args) {
        SpringApplication.run(ParseLog.class, args);
    }

    @Bean
    CommandLineRunner init(WorkflowRepositoryCustom repository) {
        return args -> {
            logger.debug("Repository: " + repository);
        };
    }

    @Override
    public void run(final String... args)  {

        try {
          logger.info("Launching application");
        }
        catch (Exception e)
        {
            logger.error("Error in main  ",e);
            e.printStackTrace();
        }
    }
}

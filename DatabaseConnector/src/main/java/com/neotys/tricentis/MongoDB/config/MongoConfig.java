package com.neotys.tricentis.MongoDB.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.neotys.tricentis.MongoDB.DatabaseException;
import com.neotys.tricentis.MongoDB.EnvironmentException;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static com.neotys.tricentis.MongoDB.config.Constants.*;

//@Configuration
//@ComponentScan({"com.neotys"})
//@EntityScan(basePackages = {"com.neotys.web.foundation"})
//@ComponentScan("com.neotys.web.foundation.DatabaseConnector.data.repository")
//@ComponentScan("com.neotys.web.foundation.log")
//@EnableMongoRepositories( {"com.neotys.web.foundation.DatabaseConnector.data.repository"})
//@EnableTransactionManagement
public class MongoConfig extends AbstractMongoConfiguration {
   private  static String MONGO_HOST;
    private  static String MONGO_DBNAME;
    private  static String MONGO_USER;
    private  static String MONGO_PASSWORD;
    private  static int MONGO_PORT;


    public MongoConfig() {
        initConnector();
    }




    @Override
    public MongoClient mongoClient() {

        ServerAddress address = new ServerAddress(MONGO_HOST, MONGO_PORT);
        MongoCredential credential = MongoCredential.createCredential(MONGO_USER, getDatabaseName(), MONGO_PASSWORD.toCharArray());
        MongoClientOptions options = new MongoClientOptions.Builder().build();

        return new MongoClient(address, credential, options);
    }





    @Override
    protected String getDatabaseName() {
        return MONGO_DBNAME;
    }




    public void initConnector()  {


              try {
                Optional<String> password= Optional.ofNullable(System.getenv(SECRET_PASSWORD)).filter(o->!o.isEmpty());
                if(!password.isPresent())
                    throw new EnvironmentException("The password environment varaible is missing");

                Optional<String> user= Optional.ofNullable(System.getenv(SECRET_USER)).filter(o->!o.isEmpty());
                if(!user.isPresent())
                    throw new EnvironmentException("The user environment varaible is missing");

                Optional<String> host= Optional.ofNullable(System.getenv(SECRET_HOST)).filter(o->!o.isEmpty());
                if(!host.isPresent())
                    throw new EnvironmentException("The host environment varaible is missing");


                Optional<String> mongoport= Optional.ofNullable(System.getenv(SECRET_PORT)).filter(o->!o.isEmpty());
                if(!mongoport.isPresent())
                    throw new EnvironmentException("The port environment varaible is missing");

                try
                {
                    MONGO_PORT=Integer.parseInt(mongoport.get());
                }
                catch (NumberFormatException e)
                {
                    throw new EnvironmentException("The port needs to be a integer");
                }
                Optional<String> databasename= Optional.ofNullable(System.getenv(SECRET_DATABASENAME)).filter(o->!o.isEmpty());
                if(!databasename.isPresent())
                    throw new EnvironmentException("The databasename environment varaible is missing");

              MONGO_HOST = host.get();

              MONGO_DBNAME= databasename.get();
              MONGO_USER=user.get();
              MONGO_PASSWORD=password.get();
            } catch (EnvironmentException e) {
                e.printStackTrace();
            }




    }

    @Bean
    public MongoDbFactory mongoDbFactory() {
        MongoDbFactory factory = new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
        return factory;
    }
    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate template = new MongoTemplate(mongoDbFactory());
        return template;
    }
}
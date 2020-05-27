package com.neotys.tricentis.stadDataparser.Config;

import com.neotys.tricentis.stadDataparser.parser.StadDataParserImpl;
import com.neotys.tricentis.stadDataparser.parser.StadDataParserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StadDataAppConfig {
    @Bean
    public StadDataParserService stadDataService() {
        return new StadDataParserImpl();
    }
}

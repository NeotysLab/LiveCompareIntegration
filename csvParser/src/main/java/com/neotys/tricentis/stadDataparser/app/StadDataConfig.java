package com.neotys.tricentis.stadDataparser.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StadDataConfig {
    private static String TESTINGDIRECTORY ;
    private static final String BUNDLE_NAME = "stadData-extraction.properties";

    public StadDataConfig() {
        initConnector();
    }

    public static String getTESTINGDIRECTORY() {
        return TESTINGDIRECTORY;
    }

    /**
     * API TOKENs and CLIENT ID come from Neotys Dynatrace SAAS account.
     */


    public  void initConnector()
    {
        String port;

        Properties prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(BUNDLE_NAME);

        if (inputStream != null) {
            try {
                prop.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("property file '" + BUNDLE_NAME + "' not found in the classpath");
        }

        TESTINGDIRECTORY = prop.getProperty("logdirectory");




    }


}

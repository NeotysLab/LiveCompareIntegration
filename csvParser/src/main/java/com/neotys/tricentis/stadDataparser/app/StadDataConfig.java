package com.neotys.tricentis.stadDataparser.app;

import com.neotys.tricentis.stadDataparser.exception.StadDataConfigExceptin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static com.neotys.tricentis.stadDataparser.Config.Constants.SECRET_DIR;

public class StadDataConfig {
    private static String TESTINGDIRECTORY ;

    public StadDataConfig() throws StadDataConfigExceptin {
        init();
    }

    public  String getTESTINGDIRECTORY() {
        return TESTINGDIRECTORY;
    }

    /**
     * API TOKENs and CLIENT ID come from Neotys Dynatrace SAAS account.
     */

    private void init() throws StadDataConfigExceptin {
        Optional<String> directory= Optional.ofNullable(System.getenv(SECRET_DIR)).filter(o->!o.isEmpty());
        if(!directory.isPresent())
            throw new StadDataConfigExceptin("The directory environment variable is missing");

        TESTINGDIRECTORY=directory.get();
    }



}

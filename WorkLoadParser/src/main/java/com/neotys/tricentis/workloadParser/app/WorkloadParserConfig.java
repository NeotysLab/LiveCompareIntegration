package com.neotys.tricentis.workloadParser.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WorkloadParserConfig {
    private static String RESULTPATH;
    private static double ORDERRATIO;
    private static double HITRATIO;
    private static double ACCEPTABLERATIO;
    private static int MINNAVIGATIONSIZE;
    private static long MAXMILLISECONDSBETWEENREQUESTS;
    private static double ACCEPTABLERATIOMID;
    private static String STATICEXPRESSION;

    public WorkloadParserConfig()
    {
        initConnector();
    }

    public String getResultPath()
    {
        return RESULTPATH;
    }

    public  double getORDERRATIO() {
        return ORDERRATIO;
    }

    public  double getHITRATIO() {
        return HITRATIO;
    }

    public  String getSTATICEXPRESSION() {
        return STATICEXPRESSION;
    }

    public long getMAXMILLISECONDSBETWEENREQUESTS()
    {
        return MAXMILLISECONDSBETWEENREQUESTS;
    }
    public int getMINNAVIGATIONSIZE()
    {
        return MINNAVIGATIONSIZE;
    }

    public  double getACCEPTABLERATIOMID() {
        return ACCEPTABLERATIOMID;
    }

    public  double getACCEPTABLERATIO() {
        return ACCEPTABLERATIO;
    }
    private  void initConnector()
    {
        /*String port;

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


        RESULTPATH=prop.getProperty("resultfolder");
        ORDERRATIO=Double.parseDouble(prop.getProperty("OrderRatio"));
        HITRATIO=Double.parseDouble(prop.getProperty("MinHitRatio"));
        ACCEPTABLERATIO=Double.parseDouble(prop.getProperty("AcceptableRatio"));
        MINNAVIGATIONSIZE=Integer.parseInt(prop.getProperty("MinNavigationsize"));
        MAXMILLISECONDSBETWEENREQUESTS=Long.parseLong(prop.getProperty("MaxMilliSecondsBetweenRequests"));
        ACCEPTABLERATIOMID=Double.valueOf(prop.getProperty("AcceptableRatioForMidCharacter"));
        STATICEXPRESSION=prop.getProperty("staticsregularExpression");*/
    }
}

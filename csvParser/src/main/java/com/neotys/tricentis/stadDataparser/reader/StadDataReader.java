package com.neotys.tricentis.stadDataparser.reader;

import com.neotys.tricentis.stadDataparser.data.StadDataraw;
import com.neotys.tricentis.stadDataparser.parser.StadDataParserImpl;
import org.slf4j.LoggerFactory;


import java.util.regex.Pattern;

public class StadDataReader {
    private final static String COLUMNSEPERATOR=",";
    private static Pattern MyPatern=Pattern.compile(COLUMNSEPERATOR);
    /*

    Example of a line :
    #Fields: date time cs-ip cs-method cs-uri sc-status sc-bytes time-taken cs(Referer) cs(User-Agent) cs(Cookie) x-Custom x-LogGroup x-disid x-extstatus x-headersize
    2018-05-26	22:51:24.876	52.68.223.178	HEAD	/assets/PRO_AUT/marketinfos.json	200	0	0	"-"	"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.67 Safari/537.36"	"-"	"-"	y	2036221	500080102	344
     */
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(StadDataReader.class);

    public static StadDataraw parse(String line )
    {

    try {


        String[] p = MyPatern.split(line);

        StadDataraw data = new StadDataraw();
        data.setIndex(cleanAndConvert(p[0]));
        data.setServer(p[1]);
        data.setStardate(p[3]);
        data.setStartTime(p[4]);
        data.setEndDate(p[5]);
        data.setEndTime(p[6]);
        data.setTcode(p[7]);
        data.setReport(p[8]);
        data.setTaskType(p[9]);
        data.setDynpron(p[10]);
        data.setAccount(p[11]);
        data.setResponseTime(cleanAndConvert(p[12]));
        data.setCputime(cleanAndConvert(p[13]));
        data.setQueueTime(cleanAndConvert(p[14]));

        data.setUsedBytes(cleanAndConvert(p[18]));

        return data;
        }
        catch (NumberFormatException e)
        {
            logger.error("Conversion issue ",e);
        }
        catch (Exception e)
        {
            logger.error("Technical  issue during conversion ",e);
        }
        return null;
    }

    private static Long cleanAndConvert(String text) throws NumberFormatException
    {

        text=text.replaceAll("(^\\h*)|(\\h*$)","");
        text=text.replaceAll("\\s+", "");
        text=text.replaceAll("\u00A0", "");
        return Long.parseLong(text);
    }
}

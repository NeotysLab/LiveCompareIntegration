package com.neotys.tricentis.workloadParser.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {
    public static long convertDate(String date) throws ParseException {

        DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date result=m_ISO8601Local.parse(date);
        return result.getTime();
    }
}

package com.neotys.tricentis.stadDataparser;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dataconverter {

    private long convertDate(String date, String time) throws ParseException {
        String globaldate=date+"T"+time+"Z";
        DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date result=m_ISO8601Local.parse(globaldate);
        return result.getTime();
    }

    @Test
    public void testconversion()
    {
        // "2020-04-27'T'11:06:10'Z'"
        String data,time;
        data="2020-04-27";
        time="11:06:10";
        try {
            long times=convertDate(data,time);
            System.out.println(String.valueOf(times));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCharacter()
    {
        String pourri="3 101";
        pourri=pourri.replaceAll("(^\\h*)|(\\h*$)","");
        pourri=pourri.replaceAll("\\s+", "");
        pourri=pourri.replaceAll("\u00A0", "");
        Long.parseLong(pourri);
        pourri.chars().forEach(value -> System.out.println(value));
    }
}

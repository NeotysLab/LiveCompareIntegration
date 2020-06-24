package com.neotys.tricentis.workloadParser.controller;

import com.neotys.tricentis.MongoDB.aggregate.TcodePerMin;
import com.neotys.tricentis.MongoDB.aggregate.TcodeUsage;
import com.neotys.tricentis.MongoDB.aggregate.TransactionPerHour;
import com.neotys.tricentis.MongoDB.aggregate.TransactionUsage;
import com.neotys.tricentis.MongoDB.data.TCodeUsagePerMin;
import com.neotys.tricentis.MongoDB.data.UserSAPSession;
import com.neotys.tricentis.MongoDB.data.repository.StadDataRepository;
import com.neotys.tricentis.MongoDB.data.repository.StadDataRepositoryCustom;
import com.neotys.tricentis.MongoDB.data.repository.TcodeUsagePerMinRepository;
import com.neotys.tricentis.MongoDB.data.repository.UserSAPSessionRepository;
import com.neotys.tricentis.workloadParser.graph.GraphUtils;
import com.neotys.tricentis.workloadParser.util.Converter;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static com.neotys.tricentis.workloadParser.app.Constants.MAX_RATIO;

@RestController
public class TcodeStatsController {

    @Autowired
    StadDataRepository stadDataRepositoryCustom;
    @Autowired
    UserSAPSessionRepository userSAPSessionRepository;
    @Autowired
    TcodeUsagePerMinRepository tcodeUsagePerMinRepository;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TcodeStatsController.class);

    //#TODO look :https://www.highcharts.com/stock/demo/stock-tools-gui
    private TimeSeriesCollection getTimeSeriesCollectionFrom(List<TcodePerMin> tCodeUsagePerMins)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        tCodeUsagePerMins.stream().forEach(tCodeUsagePerMin ->
                {

                        TimeSeries ts = new TimeSeries(tCodeUsagePerMin.getTcode());

                         tCodeUsagePerMin.getLisofUsageList().forEach(usage -> {
                             try
                             {
                                String dat=String.valueOf(tCodeUsagePerMin.getMonth())+"-"+String.valueOf(tCodeUsagePerMin.getDay())+"-"+String.valueOf(tCodeUsagePerMin.getYear())+" "+usage.getHour()+":"+String.valueOf(usage.getMinute());
                                Date d= simpleDateFormat.parse(dat);
                                RegularTimePeriod regularTimePeriod = RegularTimePeriod.createInstance(Minute.class, d, TimeZone.getDefault());
                                 String chartTitle = null;
                                 String xTitle = null;

                                 ts.add(regularTimePeriod, Integer.valueOf(usage.getNumberOfCalls()));
                             }
                             catch (ParseException e)
                             {
                                 logger.error("ParseException GettimeSeries ",e);
                             }
                             catch (Exception e)
                             {
                                 logger.error("Technical error ",e);
                             }
                        });
                        dataset.addSeries(ts);
                }
                );

        return dataset;
    }

    @GetMapping("/tcodeStatPerMinute")
    public ResponseEntity<byte []> getTcodeUsagePerMinute(String startDate,String endDate) {
        HttpStatus responseCode = HttpStatus.OK;
        byte [] image = new byte[0];


        try {
            long start = Converter.convertDate(startDate);
            long end = Converter.convertDate(endDate);
            List<TcodePerMin> tcodeUsageList=tcodeUsagePerMinRepository.getTcodePerminfromDates(start,end);
            TimeSeriesCollection timeSeriesCollection=getTimeSeriesCollectionFrom(tcodeUsageList);
            String chartTitle = " Tcode usage per Minute";
            String xTitle = "Date between "+startDate +" and "+endDate;
            String yTitle="Number of access";
            image= GraphUtils.getGraphFromDataSet(timeSeriesCollection,chartTitle,xTitle,yTitle);
            }
        catch (ParseException e)
        {
            logger.error("ParseException ",e);
            throw new InputFormatException(e.getMessage());
        }
        catch (Exception e) {
            logger.error("Technical error ",e);
            responseCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<byte[]>(image, responseCode);
    }


    @GetMapping("/tcodeStat")
    List<UserSAPSession> getStat(String startDate, String endDate)   {

        try {
            long start = Converter.convertDate(startDate);
            long end = Converter.convertDate(endDate);
            List<UserSAPSession> tcodeUsageList=userSAPSessionRepository.getUserSessionFromDate(start,end);
            if(tcodeUsageList.size()>0)
                return tcodeUsageList;
            else
                throw new StadsNotFoundException(startDate);
        }
        catch (ParseException e)
        {
            throw new InputFormatException(e.getMessage());
        }


    }

    @GetMapping("/tcodeTPH")
    List<TransactionPerHour> getTPH(String startDate,String endDate)   {

        try {
            long start = Converter.convertDate(startDate);
            long end = Converter.convertDate(endDate);
             List<TransactionPerHour> transactionUsages=stadDataRepositoryCustom.getTransactionPerHourFromDate(start,end);


            if(transactionUsages.size()>0)
                return transactionUsages;
            else
                throw new StadsNotFoundException(startDate);
        }
        catch (ParseException e)
        {
            throw new InputFormatException(e.getMessage());
        }


    }

}

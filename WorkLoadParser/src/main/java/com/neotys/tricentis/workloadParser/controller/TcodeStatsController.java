package com.neotys.tricentis.workloadParser.controller;

import com.neotys.tricentis.MongoDB.aggregate.TcodeUsage;
import com.neotys.tricentis.MongoDB.aggregate.TransactionPerHour;
import com.neotys.tricentis.MongoDB.aggregate.TransactionUsage;
import com.neotys.tricentis.MongoDB.data.repository.StadDataRepository;
import com.neotys.tricentis.MongoDB.data.repository.StadDataRepositoryCustom;
import com.neotys.tricentis.workloadParser.util.Converter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import static com.neotys.tricentis.workloadParser.app.Constants.MAX_RATIO;

@RestController
public class TcodeStatsController {

    @Autowired
    StadDataRepository stadDataRepositoryCustom;


    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TcodeStatsController.class);


    @GetMapping("/tcodeStat")
    List<TcodeUsage> getStat(String startDate,String endDate)   {

        try {
            long start = Converter.convertDate(startDate);
            long end = Converter.convertDate(endDate);
            List<TcodeUsage> tcodeUsageList=stadDataRepositoryCustom.getTcodeUsagefromDate(start,end);
            List<TransactionUsage> transactionUsages=stadDataRepositoryCustom.getTransactionUsageFromDate(start,end);

            tcodeUsageList.stream().forEach(tcodeUsage -> {
                List<TransactionUsage> singlecount=transactionUsages.stream().filter(transactionUsage -> transactionUsage.getHour()==tcodeUsage.getHour() && transactionUsage.getMinute()==tcodeUsage.getMinute()).limit(1).collect(Collectors.toList());
                tcodeUsage.defineRatio(singlecount.get(0).getNumberofcalls());
            });

            List<TcodeUsage> usageList=tcodeUsageList.stream().filter(tcodeUsage -> tcodeUsage.getRatio()>MAX_RATIO).collect(Collectors.toList());

            if(usageList.size()>0)
                return usageList;
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

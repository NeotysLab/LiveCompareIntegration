package com.neotys.tricentis.workloadParser.service;

import com.neotys.tricentis.MongoDB.aggregate.TcodeUsage;
import com.neotys.tricentis.MongoDB.aggregate.TransactionUsage;
import com.neotys.tricentis.MongoDB.aggregate.UserSession;
import com.neotys.tricentis.MongoDB.data.TCodeUsagePerMin;
import com.neotys.tricentis.MongoDB.data.UserSAPSession;
import com.neotys.tricentis.MongoDB.data.repository.StadDataRepository;
import com.neotys.tricentis.MongoDB.data.repository.WorkflowRepositoryCustom;
import com.neotys.tricentis.workloadParser.Task.WorkloadParserTask;
import com.neotys.tricentis.workloadParser.scheduler.WorkloadParserScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.neotys.tricentis.workloadParser.app.Constants.MAX_RATIO;

@Service("WorkloadPaserService")
public class WorkloadPaserService implements WorkloadSeriviceInterface{
    private static final Logger logger = LoggerFactory.getLogger(WorkloadPaserService.class);

    private WorkloadParserTask task;

    private long extractor_date;
    private long lasttcodeparser_date;
    @Autowired
    private WorkflowRepositoryCustom worflowRepository;
    @Autowired
    private StadDataRepository stadDataRepository;
    @Autowired
    private MongoOperations operations;


    private void updateTcodeTime(Date date)
    {
        long tcodeparser=task.getLasTcodeParserTime();
        if(tcodeparser==0)
        {
            task.createTcodeParserTime(date);
        }
        else
        {
            task.updateTcodeParserTime(date);
        }
    }
    @Override
    public void generateUserSession() {

        if(extractor_date>0 ) {
            if(lasttcodeparser_date==0||lasttcodeparser_date<extractor_date)
            {
                logger.info("strating the parsing for "+extractor_date);
                generateTcodeUsagePermin();
                Date tcodparseDate = new Date();
                generateSAPSession(tcodparseDate);
                updateTcodeTime(tcodparseDate);
            }
        }

    }

    private void generateSAPSession(Date extractordate)
    {
        List<UserSession> userSessions=stadDataRepository.getUserSessionFromdate(extractor_date);
        userSessions.stream().forEach(session -> {
            operations.save(new UserSAPSession(session,extractordate));
        });
    }

    private void generateTcodeUsagePermin()
    {
        logger.debug("GeenerateTcodeUsagePermin");
        List<TcodeUsage> tcodeUsageList=stadDataRepository.getTcodeUsagefromDate(extractor_date);
        List<TransactionUsage> transactionUsages=stadDataRepository.getTransactionUsageFromDate(extractor_date);


        logger.debug("LIst extracted tcodeusaelist size"+String.valueOf(tcodeUsageList.size()));
        logger.debug("LIst transactionusage size"+String.valueOf(transactionUsages.size()));

        tcodeUsageList.stream().forEach(tcodeUsage -> {
            logger.debug("Calcuclating ration for "+tcodeUsage.getTcode()+" hour "+String.valueOf(tcodeUsage.getHour()) +" minute "+String.valueOf(tcodeUsage.getMinute())+ " calls "+String.valueOf(tcodeUsage.getNumberOfCalls()));
            List<TransactionUsage> singlecount=transactionUsages.stream().filter(transactionUsage -> transactionUsage.getHour()==tcodeUsage.getHour() && transactionUsage.getMinute()==tcodeUsage.getMinute()).limit(1).collect(Collectors.toList());
            logger.debug("found the total "+String.valueOf(singlecount.get(0).getNumberofcalls())+" and the tocde numner of calls "+String.valueOf(tcodeUsage.getNumberOfCalls()));
            tcodeUsage.defineRatio(singlecount.get(0).getNumberofcalls());
        });

       // List<TcodeUsage> usageList=tcodeUsageList.stream().filter(tcodeUsage -> tcodeUsage.getRatio()>MAX_RATIO).collect(Collectors.toList());
       // logger.debug("filtering the number to transaction "+ String.valueOf(usageList.size()));
        tcodeUsageList.stream().forEach(tcodeUsage -> {
            logger.debug("Saving stats for "+tcodeUsage.getTcode() + " at "+String.valueOf(tcodeUsage.getHour())+ " minute "+String.valueOf(tcodeUsage.getMinute()+" number of calls "+String.valueOf(tcodeUsage.getNumberOfCalls())+ " ratio "+ String.valueOf(tcodeUsage.getRatio())));
            operations.save(new TCodeUsagePerMin(tcodeUsage));
        });
    }
    @Override
    public void initService() {

            task=new WorkloadParserTask(this.operations,worflowRepository);
            extractor_date=task.getLasLogParserTime();
            lasttcodeparser_date=task.getLasTcodeParserTime();

    }
}

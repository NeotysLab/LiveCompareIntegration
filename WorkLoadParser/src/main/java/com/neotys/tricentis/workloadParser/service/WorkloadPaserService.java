package com.neotys.tricentis.workloadParser.service;

import com.neotys.tricentis.MongoDB.aggregate.*;
import com.neotys.tricentis.MongoDB.data.TCodeUsagePerMin;
import com.neotys.tricentis.MongoDB.data.UserSAPSession;
import com.neotys.tricentis.MongoDB.data.repository.StadDataRepository;
import com.neotys.tricentis.MongoDB.data.repository.UserSAPSessionRepository;
import com.neotys.tricentis.MongoDB.data.repository.WorkflowRepositoryCustom;
import com.neotys.tricentis.MongoDB.spark.datamodel.Session;
import com.neotys.tricentis.workloadParser.Task.WorkloadParserTask;
import com.neotys.tricentis.workloadParser.scheduler.WorkloadParserScheduler;
import org.apache.spark.SparkConf;
import org.apache.spark.ml.fpm.FPGrowth;
import org.apache.spark.ml.fpm.FPGrowthModel;
import org.apache.spark.sql.*;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.neotys.tricentis.workloadParser.app.Constants.MAX_RATIO;
import static com.neotys.tricentis.workloadParser.app.Constants.SPARK_APPNAME;
import static com.neotys.tricentis.workloadParser.app.Constants.SPARK_PATH;

@Service("WorkloadPaserService")
public class WorkloadPaserService implements WorkloadSeriviceInterface{
    private static final Logger logger = LoggerFactory.getLogger(WorkloadPaserService.class);

    private SparkSession sparkSession;
    private WorkloadParserTask task;

    private long extractor_date;
    private long lasttcodeparser_date;
    @Autowired
    private WorkflowRepositoryCustom worflowRepository;
    @Autowired
    private StadDataRepository stadDataRepository;
    @Autowired
    private UserSAPSessionRepository userSAPSessionRepository;
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
                detectCommonPatern();

                if(sparkSession != null)
                {
                    sparkSession.close();
                }
            }
        }

    }



    private void generateSAPSession(Date extractordate)
    {
        List<UserSession> userSessions=stadDataRepository.getUserSessionFromdate(extractor_date);
        userSessions.stream().forEach(session -> {

            AtomicLong atomicLong=new AtomicLong(0);
            session.getListofSession().forEach(step -> {
                Long thinktime = atomicLong.get() > 0 ? step.getDatesession() - atomicLong.get() : 0;
                atomicLong.set(step.getDatesession());
                step.setThinktime(thinktime);
            });
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
            sparkSession = SparkSession.builder()
                .appName(SPARK_APPNAME)
                .master("local[*]")
                .config("spark.sql.warehouse.dir", "file:"+SPARK_PATH)
                .getOrCreate();
    }



    @Override
    public void detectCommonPatern() {
        List<InteractionCountbyStep> countbyStepList=userSAPSessionRepository.getInteractionCountbyStep(lasttcodeparser_date);
        List<TotalCountByStep> totalCountByStepList=userSAPSessionRepository.getTotalCountByStep(lasttcodeparser_date);
        List<String> transactionlist=new ArrayList<>();
        countbyStepList.stream().forEach(interactionCountbyStep -> {
                transactionlist.add(interactionCountbyStep.getTcode()+"_"+interactionCountbyStep.getDynpron());
                List<Integer> totalcount=totalCountByStepList.stream().filter(totalCountByStep -> totalCountByStep.getArrayIndex()==interactionCountbyStep.getArrayIndex()).limit(1).map(totalCountByStep -> totalCountByStep.getCount()).collect(Collectors.toList());
                if(totalcount.size()>0)
                {
                   interactionCountbyStep.defineRation(totalcount.get(0));
                }
        });

        List<UserSAPSession> userSAPSessions=userSAPSessionRepository.getUserSessionFromParsingDate(lasttcodeparser_date);

        List<Session> sessionList=new ArrayList<>();
        userSAPSessions.stream().forEach(sapSession -> {
            Session session=new Session(sapSession);
            sessionList.add(session);

        });
        Encoder<Session> sessionEncoder = Encoders.bean(Session.class);
        Dataset<Session> javaBeanDS = sparkSession.createDataset(
                sessionList,
                sessionEncoder
        );


        FPGrowthModel model = new FPGrowth()
                .setItemsCol("session")
                .setMinSupport(0.5)
                .setMinConfidence(0.6)
                .fit(javaBeanDS);

        model.freqItemsets().show();

        // Display generated association rules.
        model.associationRules().show();

// transform examines the input items against all the association rules and summarize the
// consequents as prediction
        model.transform(javaBeanDS).show();


    }


}

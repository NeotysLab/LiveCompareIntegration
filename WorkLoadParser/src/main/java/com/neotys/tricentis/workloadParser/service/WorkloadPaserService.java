package com.neotys.tricentis.workloadParser.service;

import com.google.common.collect.Range;
import com.neotys.tricentis.MongoDB.aggregate.*;
import com.neotys.tricentis.MongoDB.data.*;
import com.neotys.tricentis.MongoDB.data.repository.NavigationStepRepository;
import com.neotys.tricentis.MongoDB.data.repository.StadDataRepository;
import com.neotys.tricentis.MongoDB.data.repository.UserSAPSessionRepository;
import com.neotys.tricentis.MongoDB.data.repository.WorkflowRepositoryCustom;
import com.neotys.tricentis.workloadParser.Task.WorkloadParserTask;

import org.apache.spark.ml.fpm.PrefixSpan;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.neotys.tricentis.workloadParser.app.Constants.*;

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

    @Autowired
    private NavigationStepRepository navigationStepRepository;

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
        try {
            if (extractor_date > 0) {
                if (lasttcodeparser_date == 0 || lasttcodeparser_date < extractor_date)
                {
                    logger.info("strating the parsing for " + extractor_date);
                    Date tcodparseDate = new Date();
                    generateTcodeUsagePermin(tcodparseDate);
                    generateSAPSession(tcodparseDate);
                    updateTcodeTime(tcodparseDate);
                    try {
                        logger.debug("Stat detect common Patern");
                        detectCommonPatern(tcodparseDate);
                        logger.debug("Stat detect dependency flow");
                        detectDependencyFlow(tcodparseDate);

                    }
                    catch (Exception e)
                    {
                        logger.error("Technical Error on common patern ",e);
                    }

                    if(sparkSession!=null)
                        sparkSession.stop();
                }
            }
        }
        catch (Exception e)
        {
            logger.error("Technical Issue",e);
        }

    }





    private void generateSAPSession(Date extractordate)
    {
        List<UserSession> userSessions=stadDataRepository.getUserSessionFromdate(extractor_date);
        userSessions.stream().forEach(session -> {

            AtomicInteger atomicInteger,previousInteger;
            atomicInteger=new AtomicInteger(0);
            previousInteger=new AtomicInteger(0);
            List<Integer> listofindexes=new ArrayList<>();
            List<Range<Integer>>rangelist=new ArrayList<>();

            boolean requirestoCreateaNewSession=false;
            AtomicLong atomicLong=new AtomicLong(0);
            session.getListofSession().forEach(step -> {
                Long thinktime = atomicLong.get() > 0 ? step.getDatesession() - atomicLong.get() : 0;
                atomicLong.set(step.getDatesession());
                int tempint=atomicInteger.getAndIncrement();
               if(thinktime.intValue()>MAX_THINKTIME)
                {
                    logger.debug("Exceeed max thinktime"+thinktime);

                    if(previousInteger.get()>0)
                    {
                        //----requires to seperate the user flow
                        rangelist.add(Range.closed(previousInteger.get(), tempint));
                        logger.debug("Range stored " + String.valueOf(previousInteger.get()) + "  and " + String.valueOf(tempint));
                    }
                    atomicLong.set(0);
                    step.setThinktime(0);
                    previousInteger.set(atomicInteger.get());

                }
                else
                    step.setThinktime(thinktime);
            });

            if(rangelist.size()>0)
                rangelist.add(Range.closed(previousInteger.get(),atomicInteger.get()-1));
            else
            {
                //----case where the separation of the flow happens only ons between n -> max session
                if(previousInteger.get()>0)
                {
                    rangelist.add(Range.closed(previousInteger.get(),atomicInteger.get()-1));
                }
            }
            //-----get the session------------
            List<List<Step>> newsetps=new ArrayList<>();
            rangelist.forEach(integer -> {
                List<Step> tmpStepList=new ArrayList<>();
                IntStream.range(integer.lowerEndpoint(),integer.upperEndpoint()+1).forEach(value -> {
                    //---
                    tmpStepList.add(session.getListofSession().get(value));
                    logger.debug("Adding step "+value);

                });
                newsetps.add(tmpStepList);
            });
            //---------------------------------

            //------create the new sessions--------
            newsetps.stream().forEach(steps -> {
                if(steps.size()>0) {
                    UserSession newsession = new UserSession(session.getAccount(), steps, steps.get(0).getDatesession());
                    logger.debug("saving new session for user " + session.getAccount());
                    operations.save(newsession.toUserSAPSession(extractordate));
                }
                else
                {
                    logger.error("Steps have no steps stored");
                }
            });


            //---create the iniatial session
            if(rangelist.size()>0) {
                List<Step> backupSteps = new ArrayList<>();
                IntStream.range(0, rangelist.stream().findFirst().get().lowerEndpoint() - 1).forEach(value ->
                        {
                            logger.debug("Adding previous session on position " + String.valueOf(value));
                            backupSteps.add(session.getListofSession().get(value));
                        }
                );

                session.setListofSession(backupSteps);
            }
            ///---
            operations.save(session.toUserSAPSession(extractordate));
        });
    }

    private void generateTcodeUsagePermin(Date extractordate)
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
            operations.save(new TCodeUsagePerMin(tcodeUsage,extractordate));
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

    private void detectDependencyFlow(Date extractordate)
    {
        logger.debug("Getting the Userflow");
        List<UserFlow> sessionPaths=navigationStepRepository.getUserFlow(extractordate);

        logger.debug("number of Userflow"+String.valueOf(sessionPaths.size()));
        List<Row> sessionList=new ArrayList<>();
        sessionPaths.stream().filter(userFlow -> userFlow.getAccount()!=null).forEach(sapSession -> {
            logger.debug("adding step of "+sapSession.getAccount());
            sapSession.getFlow().stream().forEach(flow ->{
                if(flow.getTo()!=null)
                    logger.debug("TO : " + flow.getTcode() + " to " + flow.getTo().getTcode());
            });
            sessionList.add(RowFactory.create(sapSession.toSequence()));

        });

        StructType schema = new StructType(new StructField[]{ new StructField(
                "userflow", new ArrayType(new ArrayType(DataTypes.StringType, true),true), false, Metadata.empty())
        });

        Dataset<Row> sessionDF=sparkSession.createDataFrame(sessionList,schema);
        ///print scheme

        //---display data
        sessionDF.collectAsList().stream().forEach(row -> {
            for(int i=0;i<row.length();i++)
            {
                logger.debug("NeW ROW");
                row.getList(i).stream().forEach(o -> {
                    if(o instanceof String)
                    {
                        logger.debug(" step "+ o);
                    }

                    else
                        logger.debug("class name : "+o.getClass().getName()+" Step "+ o.toString());
                });
            }
        });

        sessionDF.printSchema();
        sessionDF.show();


        PrefixSpan prefixSpan = new PrefixSpan().setMinSupport(0.8)
                                                .setMaxPatternLength(100)
                                                .setSequenceCol("userflow");

        // Finding frequent sequential patterns
        Dataset<Row> frequentitems = prefixSpan.findFrequentSequentialPatterns(sessionDF);
        frequentitems.printSchema();

        frequentitems.foreach(row ->  {

            logger.debug("Frequent asset - size "+ String.valueOf(row.size()));
            logger.debug(row.prettyJson());
            Long freq=row.getLong(1);
            List<List<String>> seq= JavaConverters.seqAsJavaList(row.getSeq(0));
            logger.debug("Number :"+String.valueOf(freq) );
            seq.stream().forEach(strings -> {
                logger.debug("Step "+strings.stream().collect(Collectors.joining(",")));
            });
        });




    }

    @Override
    public void detectCommonPatern(Date extractordate) {

        List<InteractionCountbyStep> countbyStepList=userSAPSessionRepository.getInteractionCountbyStep(extractordate);
        List<TotalCountByStep> totalCountByStepList=userSAPSessionRepository.getTotalCountByStep(extractordate);
        List<String> transactionlist=new ArrayList<>();
        countbyStepList.stream().forEach(interactionCountbyStep -> {
                transactionlist.add(interactionCountbyStep.getTcode()+"_"+interactionCountbyStep.getDynpron());
                List<Integer> totalcount=totalCountByStepList.stream().filter(totalCountByStep -> totalCountByStep.getArrayIndex()==interactionCountbyStep.getArrayIndex()).limit(1).map(totalCountByStep -> totalCountByStep.getCount()).collect(Collectors.toList());
                if(totalcount.size()>0)
                {
                   interactionCountbyStep.defineRation(totalcount.get(0));
                }
        });

        List<UserSAPSession> userSAPSessions=userSAPSessionRepository.getUserSessionFromParsingDate(extractordate);

        //----create navigationSteps------
        buildDepencySapSesion(userSAPSessions,extractordate);

      /*  logger.debug("Numbner of Sessions "+String.valueOf(userSAPSessions.size()));
        List<Row> sessionList=new ArrayList<>();
        userSAPSessions.stream().forEach(sapSession -> {
            logger.debug("adding session of "+sapSession.getAccount());
            Session session=new Session(sapSession);
            sessionList.add(RowFactory.create(session.getSession()));

        });StructType schema = new StructType(new StructField[]{ new StructField(
                "session", new ArrayType(new ArrayType(DataTypes.StringType, false),false), false, Metadata.empty())
        });

        Dataset<Row> sessionDF=sparkSession.createDataFrame(sessionList,schema);
        for (Row freqSeq: sessionDF.collectAsList()) {
            logger.debug("Input assset" +freqSeq.prettyJson() );
        }
        PrefixSpan prefixSpan = new PrefixSpan().setMinSupport(0.5)
                                                .setMaxPatternLength(100)
                                                  .setSequenceCol("session");

        // Finding frequent sequential patterns
        Dataset<Row> frequentitems = prefixSpan.findFrequentSequentialPatterns(sessionDF);
        for (Row freqSeq: frequentitems.collectAsList()) {
            logger.debug("Frequent assset" +freqSeq.prettyJson() );
        }

*/

    }

    private void buildDepencySapSesion(List<UserSAPSession> userSAPSessionList, Date extractordate)
    {
        List<NavigationStep> navigationSteps =new ArrayList<>();
        userSAPSessionList.stream().forEach(sapSession -> {
            IntStream.range(0, sapSession.getListofSession().size())
                    .forEach(idx ->{
                        SAPStep  previsous;
                        SAPStep  next;
                        Dependency previousdepency;
                        Dependency nextdepenncy;
                        ///----if first no from
                        if(idx==0)
                        {
                            previsous=null;
                            previousdepency=null;
                        }
                        else
                        {
                            previsous=sapSession.getListofSession().get(idx-1);
                            previousdepency=new Dependency(previsous.getTcode(),previsous.getDatesession(),previsous.getDynpron());

                        }

                        if(idx<(sapSession.getListofSession().size()-1))
                        {
                            //---to step will exist
                            next=sapSession.getListofSession().get(idx+1);
                            nextdepenncy=new Dependency(next.getTcode(),next.getDatesession(),next.getDynpron());
                        }
                        else {
                            next = null;
                            nextdepenncy=null;
                        }
                        SAPStep current=sapSession.getListofSession().get(idx);

                        navigationSteps.add(new NavigationStep(extractordate,current.getDatesession(),current.getTcode(),sapSession.getAccount(),current.getDynpron(),idx,previousdepency,nextdepenncy));
                    });

        });

        this.navigationStepRepository.saveNavigationsSteps(navigationSteps);

    }

    @Override
    public void closeService() {
        if(sparkSession != null)
        {
            sparkSession.close();
        }
    }


}

package com.neotys.tricentis.stadDataparser.parser;

import com.neotys.tricentis.MongoDB.data.StadData;
import com.neotys.tricentis.MongoDB.data.repository.WorkflowRepositoryCustom;
import com.neotys.tricentis.stadDataparser.Task.StadDataTask;
import com.neotys.tricentis.stadDataparser.data.StadDataraw;
import com.neotys.tricentis.stadDataparser.reader.StadDataReader;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.*;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@Service("StadDataParserImpl")
public class StadDataParserImpl implements StadDataParserService {

    private final static String LOGEXTENSION=".csv";
    private final static String RENAMEEXTENSION=".old";
    private final static String ERROREXTENSION=".err";
    private String dirPath;

    private static final org.slf4j.Logger logger =LoggerFactory.getLogger(StadDataParserImpl.class);


    @Autowired
    WorkflowRepositoryCustom workflowRepositoryInterfaceCustom;


    @Autowired
    private MongoOperations mongoOperation;



    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    private Stream<StadDataraw> parseLogFileToList(Path logileName) throws UncheckedIOException,MalformedInputException,IOException {
        Stream<String> lines = null;
        lines= Files.lines(logileName);
        return lines.parallel()
                .skip(1)
                .map(line-> StadDataReader.parse(line));


    }

    private void parseLogFile(Path logileName, Date executiondate) throws UncheckedIOException,MalformedInputException,IOException {

        parseLogFileToList(logileName).forEach(data->storeInDb(data,executiondate));
        Path tonename=FileSystems.getDefault().getPath(logileName.toAbsolutePath()+RENAMEEXTENSION);
        try {
            Files.move(logileName, tonename);
            logger.info("Delete file :"+tonename.toString());
            Files.delete(tonename);
        }
        catch(FileAlreadyExistsException e)
        {
            System.out.println(e.fillInStackTrace());
        }

    }
    private void renameFileInError(Path dirPath)
    {
        Path torename=FileSystems.getDefault().getPath(dirPath.toAbsolutePath()+ERROREXTENSION);
        try {
            Files.move(dirPath,torename);
        } catch (IOException e) {
            logger.error("impossible to rename file "+ dirPath.toString());
        }
    }

    public void scanForLogFiles()
    {
        Path directory= Paths.get(dirPath);
        AtomicBoolean filescanned= new AtomicBoolean(false);
        Date executiondate=new Date();

        try {
            logger.debug("Looking from files in "+dirPath);
            Stream<Path> streamOfLogFiles = Files.find(directory,1,(path, basicFileAttributes) -> path.getFileName().toString().endsWith(LOGEXTENSION));
            streamOfLogFiles.parallel().forEach(path-> {
                try {
                    logger.info("Scanning file" +path.toString());
                     parseLogFile(path,executiondate);
                     filescanned.set(true);

                }
                catch(UncheckedIOException e)
                {
                    logger.error("Error parsing file "+path.toString(),e);
                    renameFileInError(path);

                }
                catch (MalformedInputException e) {
                       logger.error("Error parsing file "+path.toString(),e);
                       renameFileInError(path);

                 } catch (IOException e) {
                    logger.error("error parsing file "+path.toString(),e);
                    renameFileInError(path);
                }
            });

        } catch (IOException e) {
            logger.error("error parsing file",e);
        }
        finally {
            try {
                if(filescanned.get()) {
                    final StadDataTask task = new StadDataTask(mongoOperation, workflowRepositoryInterfaceCustom);
                    long date = task.getLasParserTime();
                    if (date == 0) {
                        date = task.createParserTime(executiondate);
                    } else
                        date = task.UpdateParserTime(executiondate);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void storeInDb(StadDataraw data, Date executiondate)  {
        logger.debug(data.getStardate()+" time:"+ data.getStartTime() +" tcode:"+data.getTcode()+" user :"+data.getAccount());
        StadData stadData= null;
        try {
            stadData = new StadData(data.getServer(),data.getStardate(),data.getStartTime(),data.getEndDate(),data.getEndTime(),data.getTcode(), data.getTaskType(),data.getResponseTime(),data.getCputime(),data.getQueueTime(),data.getUsedBytes(),data.getAccount(),data.getDynpron(),data.getReport());
            stadData.setDateindex(executiondate);
            mongoOperation.save(stadData);
        } catch (ParseException e) {
            logger.error("Parsing error- conversion issue",e);
        }

    }



}

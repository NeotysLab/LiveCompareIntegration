package com.neotys.tricentis.MongoDB.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Document(collection = "StadData")
@CompoundIndexes({
        @CompoundIndex(name = "tcode_account_server", def = "{'tcode' : 1, 'account': 1,'server':1}"),
        @CompoundIndex(name = "tasktype_startdate", def = "{'startdatems' : 1, 'taskType': 1}"),

})



public class StadData {
    @Id
    private ObjectId id;

    @Indexed//(name="dateindex", expireAfterSeconds=72000)
    private Date dateindex;

    @Indexed
    private String server;

    @Indexed
    private long startdatems;
    @Indexed
    private long enddatems;
    private String startdate;
    private String startTime ;
    private String endDate ;
    private String endTime ;
    @Indexed
    private String tcode ;
    @Indexed
    private String taskType;
    private long responseTime;
    private long cputime;
    private long queueTime;
    private long usedBytes;
    @Indexed
    private String account;
    private String dynpron;



    public String get_requestid() { return id.toHexString(); }
    public void set_requestid(ObjectId _id) { this.id = _id; }

    public Date getDateindex() {
        return dateindex;
    }

    public String getServer() {
        return server;
    }

    public long getStartdate() {
        return startdatems;
    }

    public long getEnddate() {
        return enddatems;
    }

    public String getStardate() {
        return startdate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTcode() {
        return tcode;
    }

    public String getTaskType() {
        return taskType;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public long getCputime() {
        return cputime;
    }

    public long getQueueTime() {
        return queueTime;
    }

    public long getUsedBytes() {
        return usedBytes;
    }

    public String getAccount() {
        return account;
    }

    public String getDynpron() {
        return dynpron;
    }

    public void setDateindex(Date dateindex) {
        this.dateindex = dateindex;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setStartdate(long startdate) {
        this.startdatems = startdate;
    }

    public void setEnddate(long enddate) {
        this.enddatems = enddate;
    }

    public void setStardate(String stardate) {
        this.startdate = stardate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public void setCputime(long cputime) {
        this.cputime = cputime;
    }

    public void setQueueTime(long queueTime) {
        this.queueTime = queueTime;
    }

    public void setUsedBytes(long usedBytes) {
        this.usedBytes = usedBytes;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setDynpron(String dynpron) {
        this.dynpron = dynpron;
    }

    public StadData(String server, String stardate, String startTime, String endDate, String endTime, String tcode, String taskType, long responseTime, long cputime, long queueTime, long usedBytes, String account, String dynpron) throws ParseException {
        this.server = server;
        this.startdate = stardate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.tcode = tcode;
        this.taskType = taskType;
        this.responseTime = responseTime;
        this.cputime = cputime;
        this.queueTime = queueTime;
        this.usedBytes = usedBytes;
        this.account = account;
        this.dynpron = dynpron;
        this.startdatems=convertDate(startdate,startTime);
        this.enddatems=convertDate(endDate,endTime);
    }

    private long convertDate(String date, String time) throws ParseException {
        String globaldate=date+"'T'"+time+"'Z'";
        DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date result=m_ISO8601Local.parse(globaldate);
        return result.getTime();
    }
}

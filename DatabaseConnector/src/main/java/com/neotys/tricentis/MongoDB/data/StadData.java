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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Document(collection = "StadData")
@CompoundIndexes({
        @CompoundIndex(name = "tcode_account_server", def = "{'tcode' : 1, 'account': 1,'server':1}"),
        @CompoundIndex(name = "tasktype_startdate", def = "{'startdatems' : 1, 'taskType': 1}"),

})



public class StadData {
    @Id
    private ObjectId id;

    @Indexed
    private long index;
    @Indexed//(name="dateindex", expireAfterSeconds=72000)
    private Date dateindex;

    @Indexed
    private String server;

    @Indexed
    private long startdatems;
    @Indexed
    private long enddatems;

    @Indexed
    private int hour;
    @Indexed
    private int minute;
    @Indexed
    private int second;
    private int day;
    private int month;
    private int year;

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
    private String report;

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public long getStartdatems() {
        return startdatems;
    }

    public void setStartdatems(long startdatems) {
        this.startdatems = startdatems;
    }

    public long getEnddatems() {
        return enddatems;
    }

    public void setEnddatems(long enddatems) {
        this.enddatems = enddatems;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

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

    public StadData(long index,String server, String stardate, String startTime, String endDate, String endTime, String tcode, String taskType, long responseTime, long cputime, long queueTime, long usedBytes, String account, String dynpron,String report) throws ParseException {
        this.index=index;
        this.server = server;
        this.startdate = stardate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.report=report;
        this.endTime = endTime;

        this.tcode = clean(tcode);
        this.taskType = taskType;
        this.responseTime = responseTime;
        this.cputime = cputime;
        this.queueTime = queueTime;
        this.usedBytes = usedBytes;
        this.account = account;
        this.dynpron = clean(dynpron);
        this.startdatems=convertDate(startdate,startTime);
        this.enddatems=convertDate(endDate,endTime);
        ZonedDateTime startDateFormat;
        startDateFormat=convert2ZoneDate(stardate,startTime);
        this.hour=startDateFormat.getHour();
        this.minute=startDateFormat.getMinute();
        this.second=startDateFormat.getSecond();
        this.day=startDateFormat.getDayOfMonth();
        this.month=startDateFormat.getMonthValue();
        this.year=startDateFormat.getYear();
        this.dateindex=new Date();
    }

    private String clean(String input)
    {
        input=input.replaceAll("(^\\h*)|(\\h*$)","");
        input=input.replaceAll("\\s+", "");
        input=input.replaceAll("\u00A0", "");
        return input;
    }
    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    private ZonedDateTime convert2ZoneDate(String date, String time) throws ParseException {
        String globaldate=date+"T"+time+"Z";
        DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date result=m_ISO8601Local.parse(globaldate);
        return ZonedDateTime.ofInstant(result.toInstant(),
                ZoneId.systemDefault());
    }

    private long convertDate(String date, String time) throws ParseException {
        String globaldate=date+"T"+time+"Z";
        DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date result=m_ISO8601Local.parse(globaldate);
        return result.getTime();
    }
}

package com.neotys.tricentis.stadDataparser.data;

public class StadDataraw implements iStadData {
    private long startdate;
    private long enddate;
    private String server;
    private String report;
    private String stardate;
    private String startTime = null;
    private String endDate = null;
    private String endTime = null;
    private String tcode = null;
    private String taskType=null;
    private long responseTime;
    private long cputime;
    private long queueTime;
    private long usedBytes;
    private String account;
    private String dynpron;

    public long getStartdate() {
        return startdate;
    }

    public void setStartdate(long startdate) {
        this.startdate = startdate;
    }

    public long getEnddate() {
        return enddate;
    }

    public void setEnddate(long enddate) {
        this.enddate = enddate;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getStardate() {
        return stardate;
    }

    public long getCputime() {
        return cputime;
    }

    public long getUsedBytes() {
        return usedBytes;
    }

    public String getDynpron() {
        return dynpron;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setStardate(String stardate) {
        this.stardate = stardate;
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

    @Override
    public String getServer() {
        return server;
    }

    @Override
    public String getStartDate() {
        return stardate;
    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public String getEndTime() {
        return endTime;
    }

    @Override
    public String getTcode() {
        return tcode;
    }

    @Override
    public String getTaskType() {
        return taskType;
    }

    @Override
    public long getResponseTime() {
        return responseTime;
    }

    @Override
    public long getCPUTime() {
        return cputime;
    }

    @Override
    public long getQueueTime() {
        return queueTime;
    }

    @Override
    public long getUsedByteTime() {
        return usedBytes;
    }

    @Override
    public String getDynPron() {
        return dynpron;
    }

    @Override
    public String getAccount() {
        return account;
    }
}

package com.neotys.tricentis.stadDataparser.data;

public interface iStadData {


    /**
     * Returns the server name as a string value.
     *
     * @return the  servername
     */
    String getServer();

    /**
     * Returns the startdate of the tasl as a string value.
     *
     * @return the startdate
     */
    String getStartDate();

    /**
     * Returns the start time a string value.
     *
     * @return the start time
     */
    String getStartTime();
    /**
     * Returns the end date as a string value.
     *
     * @return the end date
     */
    String getEndDate();

    /**
     * Returns the end time as a string
     * @return the end time
     */
    String getEndTime();

    /**
     * Returns the tcode a string value.
     *
     * @return the tcode
     */
    String getTcode();

    /**
     * Returns the taskType a string value.
     *
     * @return the taskType
     */
    String getTaskType();

    /**
     * Returns the response time a string value.
     *
     * @return the response time
     */
    long getResponseTime();
    /**
     * Returns the cpu time a string value.
     *
     * @return the cpu time
     */
    long getCPUTime();

    /**
     * Returns the queue time a string value.
     *
     * @return the queue time
     */
    long getQueueTime();

    /**
     * Returns the UsedByte a string value.
     *
     * @return the Used bytes
     */
    long getUsedByteTime();

    /**
     * Returns the Dyn Pron R a string value.
     *
     * @return the Dynamic procedure
     */
    String getDynPron();
    /**
     * Returns the Account a string value.
     *
     * @return the account
     */
    String getAccount();



}

package com.neotys.tricentis.workloadParser.jsonDatamodel;

import com.neotys.tricentis.MongoDB.data.TCodeUsagePerMin;

public class Points {

    long timestamp;
    long value;

    public Points(long timestamp, long value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Points(TCodeUsagePerMin tCodeUsagePerMin) {
        this.timestamp=tCodeUsagePerMin.getStartdatems();
        this.value=tCodeUsagePerMin.getNumberOfCalls();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}

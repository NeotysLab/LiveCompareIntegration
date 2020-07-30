package com.neotys.tricentis.MongoDB.data;

import com.neotys.tricentis.MongoDB.aggregate.TcodeUsage;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "TCodeUsagePerMin")
public class TCodeUsagePerMin extends TcodeUsage {
    @Id
    private ObjectId id;

    @Indexed//(name="dateindex")
    private Date dateindex;

    public TCodeUsagePerMin(TcodeUsage tcodeUsage, Date extractordate) {
        super(tcodeUsage.getHour(), tcodeUsage.getMinute(), tcodeUsage.getTcode(), tcodeUsage.getNumberOfCalls(),tcodeUsage.getStartdatems(),tcodeUsage.getDay(),tcodeUsage.getMonth(),tcodeUsage.getYear());
        this.setRatio(tcodeUsage.getRatio());
        this.dateindex=extractordate;

    }


}

package com.neotys.tricentis.MongoDB.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Worflow")

public class Worflow {

    @Id
    private ObjectId id;

    @Indexed
    private long date;

    @Indexed
    private String type;

    public Worflow(long date, String type) {
        this.date = date;
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}

package com.neotys.tricentis.MongoDB.aggregate;

import com.neotys.tricentis.MongoDB.data.Dependency;

public class Dependencies {
    Dependency from;
    Dependency to;

    public Dependencies(Dependency from, Dependency to) {
        this.from = from;
        this.to = to;
    }

    public Dependency getFrom() {
        return from;
    }

    public void setFrom(Dependency from) {
        this.from = from;
    }

    public Dependency getTo() {
        return to;
    }

    public void setTo(Dependency to) {
        this.to = to;
    }
}

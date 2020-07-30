package com.neotys.tricentis.workloadParser.jsonDatamodel;

import com.neotys.tricentis.MongoDB.aggregate.SessionPath;

public class NavigationStep {
    String from;
    String to;
    int width;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public NavigationStep(SessionPath sessionPath)
    {
        this.from=sessionPath.getTcode()+":"+sessionPath.getDynpron();
        this.to=sessionPath.getTo().getTcode()+":"+sessionPath.getTo().getDynpron();
        this.width=sessionPath.getCount();
    }

    public NavigationStep(String from, String to, int width) {
        this.from = from;
        this.to = to;
        this.width = width;
    }
}

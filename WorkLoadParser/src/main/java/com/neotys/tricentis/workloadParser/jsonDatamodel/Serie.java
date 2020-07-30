package com.neotys.tricentis.workloadParser.jsonDatamodel;

import com.neotys.tricentis.MongoDB.data.TCodeUsagePerMin;

import java.util.ArrayList;
import java.util.List;

public class Serie {
    String name;
    List<Points> pointsList;

    public Serie(String name, List<Points> pointsList) {
        this.name = name;
        this.pointsList = pointsList;
    }



    public Serie(List<TCodeUsagePerMin> tCodeUsagePerMins) {

        pointsList = new ArrayList<>();
        tCodeUsagePerMins.stream().forEach(tCodeUsagePerMin -> {
            this.name=tCodeUsagePerMin.getTcode();
            pointsList.add(new Points(tCodeUsagePerMin));
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Points> getPointsList() {
        return pointsList;
    }

    public void setPointsList(List<Points> pointsList) {
        this.pointsList = pointsList;
    }
}

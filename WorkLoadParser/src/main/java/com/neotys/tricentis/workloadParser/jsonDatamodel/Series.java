package com.neotys.tricentis.workloadParser.jsonDatamodel;

import breeze.math.Complex;
import com.neotys.tricentis.MongoDB.aggregate.TcodePerMin;
import com.neotys.tricentis.MongoDB.aggregate.TcodeUsage;
import com.neotys.tricentis.MongoDB.data.TCodeUsagePerMin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Series {
    List<Serie> serieList;

    public List<Serie> getSerieList() {
        return serieList;
    }

    public void setSerieList(List<Serie> serieList) {
        this.serieList = serieList;
    }



    public Series(List<TCodeUsagePerMin> tCodeUsagePerMins)
    {
        serieList=new ArrayList<>();
        tCodeUsagePerMins.stream()
                         .collect(
                            Collectors.groupingBy(TCodeUsagePerMin::getTcode)
                        ).forEach((s, tCodeUsagePerMins1) ->
                {
                    serieList.add(new Serie(tCodeUsagePerMins));
                });
    }
}

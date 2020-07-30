package com.neotys.tricentis.workloadParser.jsonDatamodel;

import com.neotys.tricentis.MongoDB.aggregate.SessionPath;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sankey {
    List<NavigationStep> linkDataArray;

    List<NavigationProperty> nodeDataArray;

    public Sankey(List<NavigationStep> linkDataArray, List<NavigationProperty> nodeDataArray) {
        this.linkDataArray = linkDataArray;
        this.nodeDataArray = nodeDataArray;
    }

    public List<NavigationStep> getLinkDataArray() {
        return linkDataArray;
    }

    public void setLinkDataArray(List<NavigationStep> linkDataArray) {
        this.linkDataArray = linkDataArray;
    }

    public List<NavigationProperty> getNodeDataArray() {
        return nodeDataArray;
    }

    public void setNodeDataArray(List<NavigationProperty> nodeDataArray) {
        this.nodeDataArray = nodeDataArray;
    }


    public Sankey(List<SessionPath> sessionPathList)
    {
        List<String> sessionnames=sessionPathList.stream().map(sessionPath -> {
            return sessionPath.getTcode()+":"+sessionPath.getDynpron();
        }).distinct().collect(Collectors.toList());

        nodeDataArray=new ArrayList<>();
        sessionnames.forEach(s -> {
            nodeDataArray.add(new NavigationProperty(s));
        });

        linkDataArray=new ArrayList<>();
        sessionPathList.forEach(sessionPath -> {
            linkDataArray.add(new NavigationStep(sessionPath));
        });
    }
}

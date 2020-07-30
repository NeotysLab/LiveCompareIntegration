package com.neotys.tricentis.MongoDB.aggregate;

import com.neotys.tricentis.MongoDB.data.Dependency;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SessionPath {
    int arrayIndex;
    String tcode;
    String dynpron;
    Dependency to;
    List<Dependencies> dependencies;
    double ratio;
    int count;

    public SessionPath(int arrayIndex, String tcode, String dynpron, Dependency to, List<Dependencies> dependencies, double ratio, int count) {
        this.arrayIndex = arrayIndex;
        this.tcode = tcode;
        this.dynpron = dynpron;
        this.to = to;
        this.dependencies = dependencies;
        this.ratio = ratio;
        this.count = count;
    }

    public Dependency getTo() {
        return to;
    }

    public void setTo(Dependency to) {
        this.to = to;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getFlow()
    {
        List<String> stringList=new ArrayList<>();

        return dependencies.stream().map(dependencies1 -> {
            return this.tcode+":"+this.dynpron+"|"+dependencies1.to.getTcode()+":"+dependencies1.to.getDynpron();
        }).collect(Collectors.toList());
    }

    public void setratio(int total)
    {
        this.ratio=(count*1.0d/total)*100;
    }

    public double getRatio() {
        return ratio;
    }

    public int getCount() {
        return count;
    }

    public int getArrayIndex() {
        return arrayIndex;
    }

    public void setArrayIndex(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }

    public String getDynpron() {
        return dynpron;
    }

    public void setDynpron(String dynpron) {
        this.dynpron = dynpron;
    }

    public List<Dependencies> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependencies> dependencies) {
        this.dependencies = dependencies;
    }
}

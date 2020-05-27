package com.neotys.tricentis.workloadParser.service;

import java.io.IOException;

public interface WorkloadSeriviceInterface   {
    void parseStad(String path,double order,double hit,double acceptalbe,int minsize,long maxmilli,double midratio,String staticPattern);

}

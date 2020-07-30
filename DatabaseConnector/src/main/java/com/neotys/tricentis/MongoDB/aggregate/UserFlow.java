package com.neotys.tricentis.MongoDB.aggregate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserFlow {
    String account;
    List<Flow> flow;


    public UserFlow(String account, List<Flow> flow) {
        this.account = account;
        this.flow = flow;
    }

    public String getAccount() {
        return account;
    }

    public List<String> toROw()
    {
        return  flow.stream().filter(flow1 -> flow1.getTo()!=null && flow1.getTo()!=null && flow1.getTo().getTcode()!=null)
                             .map(flow1 -> {
                                        return flow1.tcode+":"+flow1.dynpron+"|"+flow1.getTo().getTcode()+":"+flow1.getTo().getDynpron();
                            }).collect(Collectors.toList());
    }

    public List<List<String>> toSequence()
    {
        return  flow.stream().filter(flow1 ->flow1.getTcode()!=null& !flow1.getTcode().isEmpty() && flow1.getTo()!=null && flow1.getTo()!=null && flow1.getTo().getTcode()!=null && !flow1.getTo().getTcode().isEmpty())
                .map(flow1 -> {
                    return Arrays.asList(flow1.tcode+":"+flow1.dynpron,flow1.getTo().getTcode()+":"+flow1.getTo().getDynpron());
                }).collect(Collectors.toList());
    }


    public void setAccount(String account) {
        this.account = account;
    }

    public List<Flow> getFlow() {
        return flow;
    }

    public void setFlow(List<Flow> flow) {
        this.flow = flow;
    }
}

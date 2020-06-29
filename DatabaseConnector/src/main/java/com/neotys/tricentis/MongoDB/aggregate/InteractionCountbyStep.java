package com.neotys.tricentis.MongoDB.aggregate;

public class InteractionCountbyStep {
    int arrayIndex;
    String dynpron;
    String tcode;
    int count;
    double ratio;
    public InteractionCountbyStep(int index, String dynpron, String tcode, int count) {
        this.arrayIndex = index;
        this.dynpron = dynpron;
        this.tcode = tcode;
        this.count = count;
    }

    public int getArrayIndex() {
        return arrayIndex;
    }

    public void setArrayIndex(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public String getDynpron() {
        return dynpron;
    }

    public void setDynpron(String dynpron) {
        this.dynpron = dynpron;
    }

    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public void defineRation(int total)
    {
        if(total>0)
            this.ratio=(count*1.0d/total)*100;
        else
            this.ratio=0;
    }

    public double getRatio()
    {
        return this.ratio;
    }
}

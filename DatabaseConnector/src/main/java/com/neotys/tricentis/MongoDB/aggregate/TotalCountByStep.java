package com.neotys.tricentis.MongoDB.aggregate;

public class TotalCountByStep {
    int arrayIndex;

    int count;




    public TotalCountByStep(Integer arrayIndex, Integer count) {
        if(arrayIndex !=null)
            this.arrayIndex = arrayIndex.intValue();
        else
            this.arrayIndex=0;

        if(count!=null)
            this.count = count.intValue();
        else
            this.count=0;
    }

    public int getArrayIndex() {
        return arrayIndex;
    }

    public void setArrayIndex(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}

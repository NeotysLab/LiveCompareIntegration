package com.neotys.tricentis.MongoDB.aggregate;

public class TotalCountByStep {
    int arrayIndex;

    int count;




    public TotalCountByStep(int arrayIndex, int count) {
        this.arrayIndex = arrayIndex;
        this.count = count;
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

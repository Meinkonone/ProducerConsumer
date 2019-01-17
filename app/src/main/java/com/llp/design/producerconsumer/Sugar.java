package com.llp.design.producerconsumer;

/**
 * 容器类：Sugar;  生产者生产该类并放到缓冲池中，消费者从缓冲池中拿到该类并消费
 */
public class Sugar {
    private String mSugarName;
    private int mSugarId;

    public Sugar(int sugarId, String sugarName) {
        this.mSugarId = sugarId;
        this.mSugarName = sugarName;
    }

    public int getSugarId() {
        return mSugarId;
    }

    public String getSugarName() {
        return mSugarName;
    }

    @Override
    public String toString() {
        return "sugarName = " + mSugarName + ", sugarId = " + mSugarId;
    }
}

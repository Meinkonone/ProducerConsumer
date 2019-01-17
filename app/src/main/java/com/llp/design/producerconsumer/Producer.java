package com.llp.design.producerconsumer;

import android.util.Log;

import java.util.List;
import java.util.Random;

/**
 * 生产者，生产Sugar并投放到缓冲池中
 * 作用：new 一个Sugar类并投放到缓冲池中
 * 逻辑：if (缓冲池满) ->暂停生产->sleep, else -> notify生产线程->继续生产sugar类
 */
public class Producer implements Runnable {
    private List<Sugar> mSugars;
    private int mMaxLength;
    private static final String TAG = "Producer";

    public Producer(List<Sugar> queues, int maxLength) {
        this.mSugars = queues;
        this.mMaxLength = maxLength;
    }

    @Override
    public void run() {
        //执行生产者线程
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                synchronized (mSugars) {
                    if (mSugars.size() >= mMaxLength) {
                        Log.i(TAG, "queue has been full, stop producer thread and notify consumer thread");
                        mSugars.notifyAll(); //唤醒消费者
                        mSugars.wait(); //停止生产
                    } else {
                        //生成Sugar类
                        Random random = new Random();
                        int id = random.nextInt(100);
                        Sugar sugar = new Sugar(id, "大大口香糖");
                        Log.i(TAG, "producer thread has product a sugar: " + sugar.toString());
                        mSugars.add(sugar);
                    }
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Log.e(TAG, "producer thread has been interrupted, " + e);
        }
    }
}

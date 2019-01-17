package com.llp.design.producerconsumer;

import android.util.Log;

import java.util.List;

/**
 * 消费者，从Sugar缓冲池中拿出Sugar并消费
 * 作用： 从缓冲池中get一个sugar类并将该类从缓冲池中remove掉
 * 逻辑： if(缓冲池空)->暂停消费->sleep消费线程， else->notify->继续消费
 */
public class Consumer implements Runnable {
    private List<Sugar> mSugars;
    private static final String TAG = "Consumer";

    public Consumer(List<Sugar> queue) {
        mSugars = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                synchronized (mSugars) {
                    if (mSugars.size() == 0) {
                        //暂停消费，唤醒生产者继续生产
                        Log.i(TAG, "queue has been empty, stop consumer thread and notify producer thread");
                        mSugars.wait();
                        mSugars.notifyAll();
                    } else {
                        Sugar sugar = mSugars.get(0);
                        Log.i(TAG, "consumer thread consume a sugar: " + sugar.toString());
                        mSugars.remove(sugar);
                    }
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Log.e(TAG, "consumer thread has been interrupted, " + e);
        }
    }
}

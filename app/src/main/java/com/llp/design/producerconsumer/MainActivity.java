package com.llp.design.producerconsumer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private List<Sugar> mSugars;
    private static final int MAX_LENGTH = 10;
    private ExecutorService mExecutor;
    private Producer producer1, producer2, producer3;
    private Consumer consumer1, consumer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置缓冲区里面类的上限为10个
        mSugars = new ArrayList<>(MAX_LENGTH);
        mExecutor = Executors.newCachedThreadPool();
        producer1 = new Producer(mSugars, MAX_LENGTH);
        producer2 = new Producer(mSugars, MAX_LENGTH);
        producer3 = new Producer(mSugars, MAX_LENGTH);
        consumer1 = new Consumer(mSugars);
        consumer2 = new Consumer(mSugars);
        start();
    }

    /**
     * 创建一个线程池，并在线程池中执行生产者和消费者线程
     */
    private void start() {
        mExecutor.execute(producer1);
        mExecutor.execute(producer2);
        mExecutor.execute(producer3);
        mExecutor.execute(consumer1);
        mExecutor.execute(consumer2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutor.shutdownNow();
    }
}

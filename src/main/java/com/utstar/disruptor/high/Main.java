package com.utstar.disruptor.high;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author george.zhang
 * @date 2020/7/21 14:15
 * @Description
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService es1 = Executors.newFixedThreadPool(4);
        ExecutorService es2 = Executors.newFixedThreadPool(4);

        Disruptor<Trade> disruptor = new Disruptor<>(new EventFactory<Trade>() {
            @Override
            public Trade newInstance() {
                return new Trade();
            }},
                1024*1024,
                es2,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy());

        //串行
        /*disruptor.handleEventsWith(new Handler1())
                .handleEventsWith(new Handler2())
                .handleEventsWith(new Handler3());*/

        //并行
        /*disruptor.handleEventsWith(new Handler1());
        disruptor.handleEventsWith(new Handler2());
        disruptor.handleEventsWith(new Handler3());*/

        //多边形
        EventHandlerGroup<Trade> tradeEventHandlerGroup = disruptor.handleEventsWith(new Handler1(), new Handler2());
        tradeEventHandlerGroup.then(new Handler3());

        RingBuffer<Trade> ringBuffer = disruptor.start();


        CountDownLatch latch = new CountDownLatch(1);

        es1.submit(new TradePushlisher(latch,disruptor));

        latch.await();

        disruptor.shutdown();
        es1.shutdown();
        es2.shutdown();


    }
}

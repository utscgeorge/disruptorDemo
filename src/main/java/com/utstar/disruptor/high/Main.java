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
        ExecutorService es2 = Executors.newFixedThreadPool(5);

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

        //菱形
        /*EventHandlerGroup<Trade> tradeEventHandlerGroup = disruptor.handleEventsWith(new Handler1(), new Handler2());
        tradeEventHandlerGroup.then(new Handler3());*/

        //六边形:disruptor中线程数量要和handler数量大于或等于之
        /**
         *
         *       h1------>h2
         * s                        h3
         *       h4------>h5
         */
        Handler1 h1 = new Handler1();
        Handler2 h2 = new Handler2();
        Handler3 h3 = new Handler3();
        Handler4 h4 = new Handler4();
        Handler5 h5 = new Handler5();
        disruptor.handleEventsWith(h1,h4);
        disruptor.after(h1).handleEventsWith(h2);
        disruptor.after(h4).handleEventsWith(h5);
        disruptor.after(h2,h5).handleEventsWith(h3);




        RingBuffer<Trade> ringBuffer = disruptor.start();


        CountDownLatch latch = new CountDownLatch(1);

        es1.submit(new TradePushlisher(latch,disruptor));

        latch.await();

        disruptor.shutdown();
        es1.shutdown();
        es2.shutdown();
        System.out.println("处理完成!!");

    }
}

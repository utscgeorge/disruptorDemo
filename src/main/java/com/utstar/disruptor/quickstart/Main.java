package com.utstar.disruptor.quickstart;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        OrderEventFactory orderEventFactory = new OrderEventFactory();
        int ringBufferSize = 1024*1024;
        ExecutorService executorService = new ThreadPoolExecutor(10,10,0L,
                TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>(1000),new DefaultThreadFactory("disruptor"));

        /**
         * 1.orderEventFactory:事件(event)工厂
         * 2.ringBufferSize:容器的长度
         * 3.executorService:自定义线程池
         * 4.ProducerType:单生产者还是多生产者
         * 5.WaitStrategy:等待策略
         */
        //1.实例化disruptor对象
        Disruptor<OrderEvent> disruptor = new Disruptor<>(orderEventFactory,
                ringBufferSize,
                executorService,
                ProducerType.SINGLE,
                new BlockingWaitStrategy());


        //2.添加消费者监听(构建disruptor和消费者的关系)
        disruptor.handleEventsWith(new OrderEventHandler());

        //3.启动disruptor
        disruptor.start();

        //4.获取实际存储数据的容器
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        OrderEventProducer orderEventProducer = new OrderEventProducer(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);
        for(long i=0;i<100;i++){
            bb.putLong(0,i);
            //生产数据
            orderEventProducer.sendData(bb);
        }

        //5.关闭disruptor和线程池
        disruptor.shutdown();
        executorService.shutdown();

    }
}

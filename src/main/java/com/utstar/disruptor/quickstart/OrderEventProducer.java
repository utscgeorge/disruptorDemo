package com.utstar.disruptor.quickstart;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class OrderEventProducer {

    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(ByteBuffer byteBuffer){

        //从ringbuffer中获取下一个可用的序号
        long sequence = ringBuffer.next();
        try {
            //根据序号获取一个没有初始化的event对象
            OrderEvent orderEvent = ringBuffer.get(sequence);
            //对event初始化
            orderEvent.setValue(byteBuffer.getLong(0));
        }finally {
            //发布初始化过的event对象
            ringBuffer.publish(sequence);
        }
    }

}

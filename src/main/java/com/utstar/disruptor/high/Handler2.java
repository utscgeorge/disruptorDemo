package com.utstar.disruptor.high;

import com.lmax.disruptor.EventHandler;

import java.util.UUID;

/**
 * @author george.zhang
 * @date 2020/7/21 14:33
 * @Description
 */
public class Handler2 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("Handler 2 : set id");
        trade.setId(UUID.randomUUID().toString());
    }
}

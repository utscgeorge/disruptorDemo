package com.utstar.disruptor.high;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @author george.zhang
 * @date 2020/7/21 14:33
 * @Description
 */
public class Handler1 implements EventHandler<Trade>, WorkHandler<Trade> {

    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        this.onEvent(trade);
    }

    @Override
    public void onEvent(Trade trade) throws Exception {

        System.out.println("Handler 1 : set name");
        trade.setName("H1");
        Thread.sleep(1000);
    }
}

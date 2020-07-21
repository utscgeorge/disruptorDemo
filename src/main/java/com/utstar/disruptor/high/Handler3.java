package com.utstar.disruptor.high;

import com.lmax.disruptor.EventHandler;

/**
 * @author george.zhang
 * @date 2020/7/21 14:34
 * @Description
 */
public class Handler3 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("Handler 3 : name:"+trade.getName()+" ,id:"+trade.getId()+", price:"+trade.getPrice());
    }
}

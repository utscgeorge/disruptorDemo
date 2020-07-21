package com.utstar.disruptor.high;

import com.lmax.disruptor.EventHandler;

/**
 * @author george.zhang
 * @date 2020/7/21 14:34
 * @Description
 */
public class Handler5  implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("Handler 5 : get price:"+trade.getPrice());
        trade.setPrice(trade.getPrice()+6.7);
    }
}

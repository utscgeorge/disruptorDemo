package com.utstar.disruptor.high;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author george.zhang
 * @date 2020/7/21 14:23
 * @Description
 */
public class TradePushlisher implements Runnable {

    private CountDownLatch latch;
    private Disruptor<Trade> disruptor;
    private Integer PUBLISH_COUNT=1;

    public TradePushlisher(CountDownLatch latch, Disruptor<Trade> disruptor) {
        this.latch = latch;
        this.disruptor = disruptor;
    }

    @Override
    public void run() {

        TradeEventTranslator tradeEventTranslator = new TradeEventTranslator();

        for(int i=0;i<PUBLISH_COUNT;i++){
            disruptor.publishEvent(tradeEventTranslator);
        }
        latch.countDown();
    }
}

class TradeEventTranslator implements EventTranslator<Trade>{

    @Override
    public void translateTo(Trade trade, long l) {
        this.generateTrade(trade);
    }

    private void generateTrade(Trade trade) {
        trade.setPrice(10);
    }
}

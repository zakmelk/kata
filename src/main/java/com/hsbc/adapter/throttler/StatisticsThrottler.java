package com.hsbc.adapter.throttler;


import com.hsbc.adapter.eventbus.MultiThreadedEventBus;
import com.hsbc.domain.model.event.Statistics;
import com.hsbc.domain.port.event.Throttler;

import java.util.function.Predicate;

public class StatisticsThrottler extends MultiThreadedEventBus implements Throttler {
    private final Predicate<Statistics> predicate;

    public StatisticsThrottler(){
        predicate = statistics -> true;
    }

    public StatisticsThrottler(Predicate<Statistics> predicate) {
        this.predicate = predicate;
    }

    @Override
    public ThrottleResult shouldProceed(Statistics statistics) {
        if (predicate.test(statistics)) {
            return ThrottleResult.PROCEED;
        } else {
            return ThrottleResult.DO_NOT_PROCEED;
        }
    }

    @Override
    public void notifyWhenCanProceed(Statistics statistics) {
        if (ThrottleResult.PROCEED.equals(shouldProceed(statistics))) {
            publishEvent(statistics);
        }
    }
}

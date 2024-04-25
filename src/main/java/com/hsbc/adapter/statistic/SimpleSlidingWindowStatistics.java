package com.hsbc.adapter.statistic;


import com.hsbc.adapter.throttler.StatisticsThrottler;
import com.hsbc.domain.model.event.Statistics;
import com.hsbc.domain.model.event.SubscriberWithFilter;
import com.hsbc.domain.port.event.SlidingWindowStatistics;

import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimpleSlidingWindowStatistics implements SlidingWindowStatistics {

    private final BlockingQueue<Integer> measurements;
    private final StatisticsThrottler throttler;

    public SimpleSlidingWindowStatistics(int windowSize, StatisticsThrottler throttler) {
        this.measurements = new LinkedBlockingQueue<>(windowSize);
        this.throttler = throttler;
    }

    @Override
    public void add(int measurement) {
        if (measurements.remainingCapacity() == 0) {
            measurements.poll();
        }
        measurements.offer(measurement);
        throttler.notifyWhenCanProceed(getLatestStatistics());
    }

    @Override
    public void subscribeForStatistics(SubscriberWithFilter subscriber) {
        throttler.addSubscriberForFilteredEvents(StatisticsImpl.class, subscriber);
    }

    @Override
    public Statistics getLatestStatistics() {
        return new StatisticsImpl(measurements);
    }

    public BlockingQueue<Integer> getMeasurements() {
        return measurements;
    }

}

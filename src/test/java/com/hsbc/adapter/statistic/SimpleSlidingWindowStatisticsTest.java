package com.hsbc.adapter.statistic;

import com.hsbc.adapter.eventbus.SubscriberForStatisticsImpl;
import com.hsbc.adapter.throttler.StatisticsThrottler;
import com.hsbc.domain.model.event.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleSlidingWindowStatisticsTest {
    private StatisticsThrottler statisticsThrottler;
    private SimpleSlidingWindowStatistics slidingWindowStatistics;

    @BeforeEach
    void setUp() {
    }

    @Test
    void add() {
        // Given
        double threshold = 1;
        Predicate<Statistics> predicate = stat -> stat.getMean() > threshold;
        statisticsThrottler = new StatisticsThrottler(predicate);
        slidingWindowStatistics = new SimpleSlidingWindowStatistics(3, statisticsThrottler);
        // When
        slidingWindowStatistics.add(1);
        slidingWindowStatistics.add(2);
        slidingWindowStatistics.add(3);
        slidingWindowStatistics.add(4);
        // Then
        assertEquals(3, slidingWindowStatistics.getMeasurements().size());
    }

    @Test
    void subscribeForStatistics() {
        // Given
        BlockingQueue<Integer> measurements = new LinkedBlockingQueue<>(3);
        measurements.addAll(List.of(1, 2, 3));
        SubscriberForStatisticsImpl subscriber = new SubscriberForStatisticsImpl("Sub 1");
        double threshold = 1;
        Predicate<Statistics> predicate = stat -> stat.getMean() > threshold;
        statisticsThrottler = new StatisticsThrottler(predicate);
        slidingWindowStatistics = new SimpleSlidingWindowStatistics(3, statisticsThrottler);
        // When
        slidingWindowStatistics.subscribeForStatistics(subscriber);
        slidingWindowStatistics.add(1);
        slidingWindowStatistics.add(2);
        slidingWindowStatistics.add(3);
        // Then
        assertEquals(1, statisticsThrottler.getSubscribersWithFilter().size());
        assertEquals(2, subscriber.getReceivedObjects().size());

    }
}
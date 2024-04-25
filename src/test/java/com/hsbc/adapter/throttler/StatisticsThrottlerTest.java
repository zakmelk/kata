package com.hsbc.adapter.throttler;

import com.hsbc.adapter.statistic.StatisticsImpl;
import com.hsbc.domain.model.event.Statistics;
import com.hsbc.domain.port.event.Throttler;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StatisticsThrottlerTest {
    private StatisticsThrottler statisticsThrottler;

    @Test
    void shouldProceed_PROCEED() {
        // Given
        BlockingQueue<Integer> measurements = new LinkedBlockingQueue<>(3);
        measurements.addAll(List.of(1, 2, 3));
        double threshold = 1;
        Statistics statistics = new StatisticsImpl(measurements);
        Predicate<Statistics> predicate = stat -> stat.getMean() > threshold;
        statisticsThrottler = new StatisticsThrottler(predicate);
        // When
        Throttler.ThrottleResult result = statisticsThrottler.shouldProceed(statistics);
        // Then
        assertEquals(Throttler.ThrottleResult.PROCEED, result);
    }

    @Test
    void shouldProceed_DO_NOT_PROCEED() {
        // Given
        BlockingQueue<Integer> measurements = new LinkedBlockingQueue<>(3);
        measurements.addAll(List.of(1, 2, 3));
        double threshold = 2;
        Statistics statistics = new StatisticsImpl(measurements);
        Predicate<Statistics> predicate = stat -> stat.getMean() > threshold;
        statisticsThrottler = new StatisticsThrottler(predicate);
        // When
        Throttler.ThrottleResult result = statisticsThrottler.shouldProceed(statistics);
        // Then
        assertEquals(Throttler.ThrottleResult.DO_NOT_PROCEED, result);
    }

    @Test
    void notifyWhenCanProceed() {
        // Given
        BlockingQueue<Integer> measurements = new LinkedBlockingQueue<>(3);
        measurements.addAll(List.of(1, 2, 3));
        Statistics statistics = new StatisticsImpl(measurements);
        StatisticsThrottler statThrottler = spy(StatisticsThrottler.class);
        when(statThrottler.shouldProceed(any())).thenReturn(Throttler.ThrottleResult.PROCEED);
        // When
        statThrottler.notifyWhenCanProceed(statistics);
        // Then
        verify(statThrottler).publishEvent(statistics);
    }
}
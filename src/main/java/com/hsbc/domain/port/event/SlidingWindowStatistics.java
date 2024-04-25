package com.hsbc.domain.port.event;


import com.hsbc.domain.model.event.Statistics;
import com.hsbc.domain.model.event.SubscriberWithFilter;

public interface SlidingWindowStatistics {
    void add(int measurement);

    void subscribeForStatistics(SubscriberWithFilter subscriber);

    Statistics getLatestStatistics();
}


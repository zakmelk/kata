package com.hsbc.eventbus;


public interface SlidingWindowStatistics {
    void add(int measurement);

    void subscribeForStatistics(SubscriberWithFilter subscriber);

    Statistics getLatestStatistics();
}


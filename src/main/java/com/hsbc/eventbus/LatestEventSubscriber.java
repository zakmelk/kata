package com.hsbc.eventbus;

public interface LatestEventSubscriber extends Subscriber {
    void onLatestEvent(Object event);
}


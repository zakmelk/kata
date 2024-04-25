package com.hsbc.domain.model.event;

public interface LatestEventSubscriber extends Subscriber {
    void onLatestEvent(Object event);
}


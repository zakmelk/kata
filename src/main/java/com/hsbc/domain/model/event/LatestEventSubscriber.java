package com.hsbc.domain.model.event;

import com.hsbc.domain.model.event.Subscriber;

public interface LatestEventSubscriber extends Subscriber {
    void onLatestEvent(Object event);
}


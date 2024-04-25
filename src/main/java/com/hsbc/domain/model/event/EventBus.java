package com.hsbc.domain.model.event;

public interface EventBus {
    void publishEvent(Object o);

    void addSubscriber(Class<?> eventType, Subscriber subscriber);

    void addSubscriberForFilteredEvents(Class<?> eventType, SubscriberWithFilter subscriber);
}
